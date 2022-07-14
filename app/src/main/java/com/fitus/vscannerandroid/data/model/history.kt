package com.fitus.vscannerandroid.data.model

class History {
    private var mName: String? = null
    private var mImage = 0

    fun History(mName: String?, mImage: Int) {
        this.mName = mName
        this.mImage = mImage
    }

    fun getName(): String? {
        return mName
    }

    fun setName(mName: String?) {
        this.mName = mName
    }

    fun getImage(): Int {
        return mImage
    }

    fun setImage(mImage: Int) {
        this.mImage = mImage
    }
}