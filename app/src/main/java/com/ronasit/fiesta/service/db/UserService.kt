package com.ronasit.fiesta.service.db

import com.ronasit.fiesta.model.User
import com.ronasit.fiesta.service.IService
import io.realm.Realm

interface IUserService: IService {
    fun findUser(): User?
    fun insertUser(user: User): User
    fun updateUser(user: User)
    fun deleteUser()
    fun isUserExist(): Boolean
    fun isUserCompleted(user: User): Boolean
}

class UserService : IUserService {

    private var realm: Realm = Realm.getDefaultInstance()

    override fun findUser(): User? {
        return realm.where(User::class.java).findFirst()
    }

    override fun insertUser(user: User): User {
        if (isUserExist()) deleteUser()
        realm.beginTransaction()
        val realmUser = realm.copyToRealm(user)
        realm.commitTransaction()

        return realmUser
    }

    override fun updateUser(user: User) {
        if (findUser()?.phoneNumber != user.phoneNumber) deleteUser()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(user)
        realm.commitTransaction()
    }

    override fun deleteUser() {
        val realmUser = findUser()
        realm.beginTransaction()
        realmUser!!.deleteFromRealm()
        realm.commitTransaction()
    }

    override fun isUserExist(): Boolean {
        return findUser() != null
    }

    override fun isUserCompleted(user: User): Boolean {
        return !user.firstName.isNullOrEmpty() && !user.token.isNullOrEmpty()
    }

    override fun close() {
        realm.close()
    }
}