package com.service;

public class Review {
	private int userId;
    private int rating;
    private String comment;

    public Review(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" + "rating=" + rating + ", comment='" + comment + '\'' + '}';
    }

	public int getUserId() {
		// TODO Auto-generated method stub
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}

