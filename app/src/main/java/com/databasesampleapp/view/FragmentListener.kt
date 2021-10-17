package com.databasesampleapp.view

import com.databasesampleapp.db.Repository

interface FragmentListener {
    fun getRepository(): Repository
    fun switchActivity()
}
