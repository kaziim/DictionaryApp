package com.kazim.dictionaryapp

class Word {
    var category:String = ""
    var word:String = ""
    var definition:String = ""
    var example:String = ""
    var synonyms:String = ""
    var antonyms:String = ""

    constructor(category: String, word: String, definition: String, example: String, synonyms: String, antonyms: String) {
        this.category = category
        this.word = word
        this.definition = definition
        this.example = example
        this.synonyms = synonyms
        this.antonyms = antonyms
    }
}