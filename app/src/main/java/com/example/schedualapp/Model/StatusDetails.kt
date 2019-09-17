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
    var extraNote: String = ""
    var location: String = ""
    var contNum:String = ""

    constructor()
    constructor(id: Int,Title: String,Date: String,Time: String,From: String,To: String,ExtraNote: String,LocationSet: String) {
        this.id = id
        this.title = Title
        this.date = Date
        this.time = Time
        this.from = From
        this.to = To
        this.extraNote = ExtraNote
        this.location = LocationSet

    }

    constructor(id: Int,Title: String,Date: String,Time: String,With: String,Place: String,locationMeetingSet: String,_1:String?,_2:String?) {
        this.id = id
        this.title = Title
        this.date = Date
        this.time = Time
        this.with = With
        this.place = Place
        this.location = locationMeetingSet
    }
    constructor(id: Int,Title: String,Date: String,Time: String,To: String,locationCallSet: String) {
        this.id = id
        this.title = Title
        this.date = Date
        this.time = Time
        this.to = To
        this.location = locationCallSet

    }

    constructor(id: Int,Title: String,Date: String,Time: String,To: String,locationCallSet: String,contNum:String) {
        this.id = id
        this.title = Title
        this.date = Date
        this.time = Time
        this.to = To
        this.location = locationCallSet
        this.contNum = contNum
    }







}