package com.example.schedualapp.Model

class StatusDetails {
    var id: Int = 0
    var title: String = ""
    var date: String = ""
    var time: String = ""
    var from: String = ""
    var to: String = ""
    var with: String = ""
    var place: String = ""
    var timeBefore: String = ""
    var workTask: String = ""
    var workExtra: String = ""
    var extraNote: String = ""
    var location: String = ""

    constructor()
    constructor(id: Int,Title: String,Date: String,Time: String,From: String,To: String,ExtraNote: String,TimeBefore: String,LocationSet: String) {
        this.id = id
        this.title = Title
        this.date = Date
        this.time = Time
        this.from = From
        this.to = To
        this.timeBefore = TimeBefore
        this.extraNote = ExtraNote
        this.location = LocationSet

    }

    constructor(id: Int,Title: String,Date: String,Time: String,With: String,Place: String,TimeBefore: String,locationMeetingSet: String) {
        this.id = id
        this.title = Title
        this.date = Date
        this.time = Time
        this.with = With
        this.place = Place
        this.timeBefore = TimeBefore
        this.location = locationMeetingSet
    }

    constructor(id: Int,Title: String,Date: String,Time: String,To: String,TimeBefore: String,locationCallSet: String) {
        this.id = id
        this.title = Title
        this.date = Date
        this.time = Time
        this.to = To
        this.timeBefore = TimeBefore
        this.location = locationCallSet
    }







}