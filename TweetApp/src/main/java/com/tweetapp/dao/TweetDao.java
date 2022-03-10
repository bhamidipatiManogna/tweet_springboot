package com.tweetapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.tweetapp.model.Comments;
import com.tweetapp.model.Likes;
import com.tweetapp.model.Users;
import com.tweetapp.model.userTweets;
import com.tweetapp.util.TweetAppEncryption;

public class TweetDao {

	public TweetDao() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static TweetAppEncryption encryption=new TweetAppEncryption();
	
	DataSource ds=DataSourceFactory.getMySQLDataSource();
	public List<Users> getData() {
		List<Users> allusers = new ArrayList<Users>();
		try {
			Connection con =ds.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from users");
			while (rs.next()) {

				Users user = new Users();
				user.setFirstName(rs.getString(1));
				user.setLastName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setLoginId(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setContactNo(rs.getString(6));
				allusers.add(user);

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return allusers;
	}

	public List<userTweets> getAllTweets() {
		List<userTweets> allTweets = new ArrayList<userTweets>();
		try {
			Connection con =ds.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from usertweets");
			while (rs.next()) {

				userTweets userTweet = new userTweets();
				userTweet.setTweetId(rs.getString(1));
				userTweet.setUserName(rs.getString(2));
				userTweet.setTweetMsg(rs.getString(3));
				userTweet.setTweetTag(rs.getString(4));

				allTweets.add(userTweet);

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return allTweets;
	}
	
	public List<Comments> getComments(String tweetId) {
		List<Comments> allComments = new ArrayList<Comments>();
		try {
			Connection con =ds.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from tweetcomments where tweetId = '" + tweetId + "'");
			while (rs.next()) {

				Comments comment = new Comments();
				comment.setTweetId(rs.getString(1));
				comment.setLoginId(rs.getString(2));
				comment.setComment(rs.getString(3));

				allComments.add(comment);

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return allComments;
	}
	
	public List<Likes> getLikes(String tweetId) {
		List<Likes> allLikes = new ArrayList<Likes>();
		try {
			Connection con =ds.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from tweetLikes where tweetId = '" + tweetId + "'");
			while (rs.next()) {

				Likes like = new Likes();
				like.setTweetId(rs.getString(1));
				like.setLoginId(rs.getString(2));

				allLikes.add(like);

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return allLikes;
	}



	public int registerUser(Users user) {
		int result = 0;
		try {
			Connection con =ds.getConnection();
			PreparedStatement smt = con.prepareStatement("insert into users values(?,?,?,?,?,?)");
			smt.setString(1, user.getFirstName());
			smt.setString(2, user.getLastName());
			smt.setString(3, user.getEmail());
			smt.setString(4, user.getLoginId());
			smt.setString(5, encryption.encrypt(user.getPassword(), "secretKey"));
			smt.setString(6,  user.getContactNo());
			result = smt.executeUpdate();
			con.close();
		} catch (Exception e) {
			if(e instanceof SQLIntegrityConstraintViolationException)
				System.out.println("User name already exists");
			else
			System.out.println(e);
		}

		return result;
	}

	public int registerTweet(userTweets userT) {
		int result = 0;
		try {
			Connection con =ds.getConnection();
			PreparedStatement smt = con.prepareStatement("insert into usertweets values(?,?,?,?)");
			smt.setString(1, userT.getTweetId());
			smt.setString(2, userT.getUserName());
			smt.setString(3, userT.getTweetMsg());
			smt.setString(4, userT.getTweetTag());

			result = smt.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}
	
	public int updateTweet(userTweets userT, String tweetId) {
		int result = 0;
		try {
			String tweetMsg = userT.getTweetMsg();
			Connection con =ds.getConnection();
			PreparedStatement smt = con.prepareStatement("update usertweets set tweetMsg = '" + tweetMsg + "' where tweetId = '" + tweetId + "'");

			result = smt.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}
	
	public int likeTweet(String userName, String tweetId) {
		int result = 0;
		try {
			Connection con =ds.getConnection();
			PreparedStatement smt = con.prepareStatement("insert into tweetLikes values (?,?)");
			smt.setString(1, tweetId);
			smt.setString(2, userName);
			
			result = smt.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}
	
	
	public int replyTweet(String userName, String tweetId, Comments comment) {
		int result = 0;
		try {
			Connection con =ds.getConnection();
			PreparedStatement smt = con.prepareStatement("insert into tweetcomments (tweetId, loginId, comment) values (?,?,?)");
			smt.setString(1, tweetId);
			smt.setString(2, userName);
			smt.setString(3, comment.getComment());
			
			result = smt.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}

	public int deleteTweet(String tweetId) {
		int result = 0;
		try {
			Connection con =ds.getConnection();
			PreparedStatement smt = con.prepareStatement("delete from usertweets where tweetId = '" + tweetId + "'");

			result = smt.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}

	public List<userTweets> getAllMyTweets(String userName) {
		List<userTweets> allTweets = new ArrayList<userTweets>();
		try {
			Connection con =ds.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from usertweets where loginId = '" + userName + "'");
			while (rs.next()) {

				userTweets userTweet = new userTweets();
				userTweet.setTweetId(rs.getString(1));
				userTweet.setUserName(rs.getString(2));
				userTweet.setTweetMsg(rs.getString(3));
				userTweet.setTweetTag(rs.getString(4));

				allTweets.add(userTweet);

			}
			con.close();

		} catch (Exception e) {

		}
		return allTweets;
	}

	public int updatePassword(String userName, String newPassword, String email) {
		int result = 0;
		try {
			Connection con =ds.getConnection();
			PreparedStatement smt = con.prepareStatement("update users set password=? where email=? and loginId=?");

			smt.setString(1, encryption.encrypt(newPassword, "secretKey"));
			smt.setString(2, email);
			smt.setString(3, userName);

			result = smt.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}

	public int unlikeTweet(String userName, String tweetId) {
		int result = 0;
		try {
			Connection con =ds.getConnection();
			PreparedStatement smt = con.prepareStatement("DELETE FROM tweetLikes WHERE tweetId=? and loginId=?");
			smt.setString(1, tweetId);
			smt.setString(2, userName);
			
			result = smt.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}
}
