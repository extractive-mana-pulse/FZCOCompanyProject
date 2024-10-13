package com.example.worktestcomposeproject

import org.junit.Test

class TestJsonFile {
    @Test
    fun testJsonFile() {
        val testJson = """
[
    {
        "email": "test@example.com",
        "gender": "female",
        "phone_number": "123-456-7890",
        "birthdate": 1234567890,
        "location": {
            "street": "123 Test St",
            "city": "Test City",
            "state": "Test State",
            "postcode": 12345
        },
        "username": "testuser",
        "password": "testpass",
        "first_name": "Test",
        "last_name": "User",
        "title": "Ms",
        "picture": "path/to/image.jpg"
    }
]
"""
        val fakeUsersList = parseJsonUser(testJson)
        println(fakeUsersList)
    }
}