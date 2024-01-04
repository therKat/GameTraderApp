package me.namnamnam.mvvm.data.model

class Transaction(
    val created_at: String,
    val game_id: Int,
    val id: Int,
    val price: Int,
    val status: String,
    val updated_at: String,
    val user_id: Int
)