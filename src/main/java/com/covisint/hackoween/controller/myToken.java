
        package com.covisint.hackoween.controller;
        import java.io.Serializable;

        public class myToken
          implements Serializable
        {
          private static final long serialVersionUID = -8310437144858143806L;
          String creator;
          String creatorAppId;
          String creation;
          String access_token;
          String expires_in;
          String token_type;
          String expirationTime;
          String issueTime;

          public String getCreator()
          {
            return this.creator;
          }
          public void setCreator(String creator) {
            this.creator = creator;
          }
          public String getCreatorAppId() {
            return this.creatorAppId;
          }
          public void setCreatorAppId(String creatorAppId) {
            this.creatorAppId = creatorAppId;
          }
          public String getCreation() {
            return this.creation;
          }
          public void setCreation(String creation) {
            this.creation = creation;
          }
          public String getAccess_token() {
            return this.access_token;
          }
          public void setAccess_token(String access_token) {
            this.access_token = access_token;
          }
          public String getExpires_in() {
            return this.expires_in;
          }
          public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
          }
          public String getToken_type() {
            return this.token_type;
          }
          public void setToken_type(String token_type) {
            this.token_type = token_type;
          }
          public String getExpirationTime() {
            return this.expirationTime;
          }
          public void setExpirationTime(String expirationTime) {
            this.expirationTime = expirationTime;
          }
          public String getIssueTime() {
            return this.issueTime;
          }
          public void setIssueTime(String issueTime) {
            this.issueTime = issueTime;
          }
        }