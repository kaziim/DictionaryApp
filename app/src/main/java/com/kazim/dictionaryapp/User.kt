package com.kazim.dictionaryapp

class User {
    var fullName:String = ""
    var age:String = ""
    var email:String = ""

    constructor()
    constructor(fullName: String, age: String, email: String) {
        this.fullName = fullName
        this.age = age
        this.email = email
    }

}