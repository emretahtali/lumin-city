package com.example.huckathon.data.habits
/*
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class HabitManager(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private fun userDoc(userId: String) =
        firestore.collection("users").document(userId)


    suspend fun recordChoice(userId: String, choice: String) {
        userDoc(userId)
            .set(mapOf("history" to FieldValue.arrayUnion(choice)),
                com.google.firebase.firestore.SetOptions.merge())
            .await()
    }

    suspend fun getLastThree(userId: String): List<String> {
        val snapshot = userDoc(userId).get().await()
        val history = snapshot.get("history") as? List<*> ?: emptyList<Any>()
        @Suppress("UNCHECKED_CAST")
        val list = (history as List<String>)
        return list.takeLast(3)
    }

    suspend fun getMostFrequent(userId: String): List<String> {
        val snapshot = userDoc(userId).get().await()
        val history = snapshot.get("history") as? List<*> ?: emptyList<Any>()
        @Suppress("UNCHECKED_CAST")
        val list = (history as List<String>)
        return list.groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .take(3)
            .map { it.key }
    }
}*/


class HabitManager {

    private val historyMap = mutableMapOf<String, MutableList<String>>()

    suspend fun recordChoice(userId: String, choice: String) {
        val list = historyMap.getOrPut(userId) { mutableListOf() }
        list.add(choice)
    }

    suspend fun getLastThree(userId: String): List<String> {
        val list = historyMap[userId] ?: emptyList()
        return list.takeLast(3)
    }

    suspend fun getMostFrequent(userId: String): List<String> {
        val list = historyMap[userId] ?: emptyList()
        return list
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .take(3)
            .map { it.key }
    }

    fun clearAll() {
        historyMap.clear()
    }
}