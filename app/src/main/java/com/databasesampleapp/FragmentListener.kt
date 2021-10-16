package com.databasesampleapp

import com.databasesampleapp.db.Repository

interface FragmentListener {
    fun getRepository(): Repository
    fun switchActivity()
}
