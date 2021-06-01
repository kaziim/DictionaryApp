package com.kazim.dictionaryapp

data class Word(
    var category: String? = null,
    var word: String? = null,
    var definition: String? = null,
    var example: String? = null,
    var synonyms: String? = null,
    var antonyms: String? = null
) {

}