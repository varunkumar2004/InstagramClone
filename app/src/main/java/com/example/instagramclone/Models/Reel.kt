package com.example.instagramclone.Models

class Reel {
    var reelUrl: String? = null
    var caption: String? = null
    var profileLink: String? = null

    constructor()

    constructor(reelUrl: String?, caption: String?) {
        this.reelUrl = reelUrl
        this.caption = caption
    }

    constructor(reelUrl: String?, caption: String?, profileLink: String?) {
        this.reelUrl = reelUrl
        this.caption = caption
        this.profileLink = profileLink
    }
}