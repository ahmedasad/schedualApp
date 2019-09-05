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
    constructor(
        id: Int,
        travelTitle: String,
        travelDate: String,
        travelTime: String,
        travelFrom: String,
        travelTo: String,
        travelExtraNote: String,
        travelTimeBefore: String,
        travelLocationSet: String
    ) {
        this.id = id
        this.title = travelTitle
        this.date = travelDate
        this.time = travelTime
        this.from = travelFrom
        this.to = travelTo
        this.timeBefore = travelTimeBefore
        this.extraNote = travelExtraNote
        this.location = travelLocationSet

    }

    constructor(
        id: Int,
        meetingTitle: String,
        meetingDate: String,
        meetingTime: String,
        meetingWith: String,
        meetingPlace: String,
        meetingTimeBefore: String,
        locationMeetingSet: String,
        _1: String?,
        _2: String?
    ) {
        this.id = id
        this.title = meetingTitle
        this.date = meetingDate
        this.time = meetingTime
        this.with = meetingWith
        this.place = meetingPlace
        this.timeBefore = meetingTimeBefore
        this.location = locationMeetingSet
    }

    constructor(
        id: Int,
        callTitle: String,
        callDate: String,
        callTime: String,
        callTo: String,
        callTimeBefore: String,
        locationCallSet: String
    ) {
        this.id = id
        this.title = callTitle
        this.date = callDate
        this.time = callTime
        this.to = callTo
        this.timeBefore = callTimeBefore
        this.location = locationCallSet
    }

    constructor(
        id: Int,
        taskTitle: String,
        taskDate: String,
        taskTime: String,
        taskWork: String,
        taskTimeBefore: String,
        locationTaskSet: String,
        _6: String,
        _5: String?,
        _3: String?,
        _4: String?,
        _1: String?,
        _2: String?
    ) {
        this.id = id
        this.title = taskTitle
        this.date = taskDate
        this.time = taskTime
        this.timeBefore = taskTimeBefore
        this.workTask = taskWork
        this.location = locationTaskSet

    }

    constructor(
        id: Int,
        DinnerTitle: String,
        DinnerDate: String,
        DinnerTime: String,
        DinnerWith: String,
        DinnerPlace: String,
        DinnerTimeBefore: String,
        locationDinnerSet: String
    ) {
        this.id = id
        this.title = DinnerTitle
        this.date = DinnerDate
        this.time = DinnerTime
        this.with = DinnerWith
        this.place = DinnerPlace
        this.timeBefore = DinnerTimeBefore
        this.location = locationDinnerSet
    }

    constructor(
        id: Int, extraTitle: String,
        extraDate: String,
        extraTime: String,
        extraWork: String,
        extraTimeBefore: String,
        locationExtraSet: String,
        _5: String?,
        _6: String,
        _7: String,
        _4: String?,
        _3: String?,
        _1: String?,
        _2: String?
    ) {
        this.id = id
        this.title = extraTitle
        this.date = extraDate
        this.time = extraTime
        this.workExtra = extraWork
        this.timeBefore = extraTimeBefore

        this.location = locationExtraSet
    }

}