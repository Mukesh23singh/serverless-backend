package ru.angrytit.model;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public class CommonRequest {
    //confirm request fields
    private String id;
    private String code;

    //signup request fields
    private String name;
    private String title;
    private String businessName;
    private String email;
    private String password;
    private String type;

    //login request fields
    private String accessToken;
    private String idToken;
    private String refreshToken;

    //change password fields
    private String previousPassword;
    private String proposedPassword;

    //update user info
    private String weAre;
    private String facebookPage;
    private String twitterAddress;
    private String instagramPage;

    //update user info special for product manufacture
    private String youCanFindUsHere;
    private String weAreUnique;
    private String companyStory;
    private String founderInfo;

    //update user info special for dispensary
    private String storeAddress;
    private String websiteAddress;
    private String callUsHere;
    private String emailUsHere;

    public String getWeAre() {
        return weAre;
    }

    public void setWeAre(String weAre) {
        this.weAre = weAre;
    }

    public String getFacebookPage() {
        return facebookPage;
    }

    public void setFacebookPage(String facebookPage) {
        this.facebookPage = facebookPage;
    }

    public String getTwitterAddress() {
        return twitterAddress;
    }

    public void setTwitterAddress(String twitterAddress) {
        this.twitterAddress = twitterAddress;
    }

    public String getInstagramPage() {
        return instagramPage;
    }

    public void setInstagramPage(String instagramPage) {
        this.instagramPage = instagramPage;
    }

    public String getYouCanFindUsHere() {
        return youCanFindUsHere;
    }

    public void setYouCanFindUsHere(String youCanFindUsHere) {
        this.youCanFindUsHere = youCanFindUsHere;
    }

    public String getWeAreUnique() {
        return weAreUnique;
    }

    public void setWeAreUnique(String weAreUnique) {
        this.weAreUnique = weAreUnique;
    }

    public String getCompanyStory() {
        return companyStory;
    }

    public void setCompanyStory(String companyStory) {
        this.companyStory = companyStory;
    }

    public String getFounderInfo() {
        return founderInfo;
    }

    public void setFounderInfo(String founderInfo) {
        this.founderInfo = founderInfo;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getWebsiteAddress() {
        return websiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress) {
        this.websiteAddress = websiteAddress;
    }

    public String getCallUsHere() {
        return callUsHere;
    }

    public void setCallUsHere(String callUsHere) {
        this.callUsHere = callUsHere;
    }

    public String getEmailUsHere() {
        return emailUsHere;
    }

    public void setEmailUsHere(String emailUsHere) {
        this.emailUsHere = emailUsHere;
    }

    public String getPreviousPassword() {
        return previousPassword;
    }

    public void setPreviousPassword(String previousPassword) {
        this.previousPassword = previousPassword;
    }

    public String getProposedPassword() {
        return proposedPassword;
    }

    public void setProposedPassword(String proposedPassword) {
        this.proposedPassword = proposedPassword;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
