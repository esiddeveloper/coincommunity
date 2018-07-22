package com.kidnapsteal.coincommunity.data.local.database


import androidx.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import org.junit.Before
import com.kidnapsteal.coincommunity.data.local.dao.UserDao
import com.kidnapsteal.coincommunity.data.local.entity.User
import com.kidnapsteal.coincommunity.utils.TestUtil
import org.junit.After
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    lateinit var mUserDao: UserDao
    lateinit var mDb: AppDatabase
    lateinit var users: List<User>

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        mUserDao = mDb.userDao()

        users = TestUtil.createRandomUsers(5)
        mUserDao.insertAll(users)

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertSingleUserToDatabase() {

        val expectedUserCount = 6

        val user = TestUtil.createRandomUser()
        mUserDao.insert(user)

        mUserDao.getAllUser().test().assertValue {
            it.size == expectedUserCount
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertListOfUserToDatabase() {
        val expectedUserCount = 10
        users = TestUtil.createRandomUsers(5)
        mUserDao.insertAll(users)

        mUserDao.getAllUser().test().assertValue {
            it.size == expectedUserCount
        }
    }

    @Test
    @Throws(Exception::class)
    fun selectUserById() {
        users[2].userId = "expectedUserId"
        val expectedUserId = "expectedUserId"
        val expectedUser = users[2]
        mUserDao.insertAll(users)


        mUserDao.getUserById(expectedUserId).test().assertValue(expectedUser)
    }

    @Test
    @Throws(Exception::class)
    fun deleteUser() {
        val expectedUserCount = 4
        mUserDao.deleteUser(users[0])
        mUserDao.getAllUser().test().assertValue {
            it.size == expectedUserCount
        }
    }

}