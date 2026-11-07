package com.example.ch13102assing3

import androidx.compose.runtime.snapshots.SnapshotStateList

data class User (
    val fulName: String,
    val age: Int,
    val birthday: String,
    val address: String,
    val username: String,
    val isVerified: Boolean,
    val likeCount: Int

) {
    //Challenge 1
    fun getAgeGroup(age: Int): String {
        return when (age){
            in 0..12 -> "Child"
            in 13..17 -> "Teenager"
            in 18..59 -> "Adult"
            else -> "Senior"
        }
    }

    fun updateProfile(newUsername: String, newAge: Int): User {
        return copy(
            username = newUsername,
            age = newAge
        )
    }

    fun addFriend(friendList: SnapshotStateList<String>, newFriend: String){
        if ( newFriend.isNotBlank() && !friendList.contains(newFriend)){
            friendList.add(newFriend)
        }
    }
    //Challenge 2
    fun removeFriend(friendList: SnapshotStateList<String>, friendName: String) {
        friendList.remove(friendName)
    }
}