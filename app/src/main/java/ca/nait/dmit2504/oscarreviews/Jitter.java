package ca.nait.dmit2504.oscarreviews;

class Jitter {
    // Define data fields to store data
    private String date;
    private String reviewer;
    private String category;
    private String nominee;
    private String review;

    // Create/generate the getters/setters for each data field
    // The getters/setters are the properties

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }


    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(final String reviewer) {
        this.reviewer = reviewer;
    }


    public String getCategory() { return category; }

    public void setCategory(final String category) { this.category = category; }


    public String getNominee() { return nominee; }

    public void setNominee(final String nominee) { this.nominee = nominee; }


    public String getReview() { return review; }

    public void setReview(final String review) { this.review = review; }
}
