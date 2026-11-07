package com.example.ch13102assing3

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ch13102assing3.ui.theme.Ch13102assing3Theme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           UserProfileScreen()
            }
        }
}

@Composable
fun UserProfileScreen(){
    var user1 by remember{
        mutableStateOf(
            User (
                 "Rich Dillard",
               35,
                "07/29/1991",
                "Livermore",
                "userName",
                 true,
               0

            )
        )
    }
    // Challenge 3
    val friends = remember {
        mutableStateListOf("Rich","Addie","Lindsey","Liam")
    }

    // Challenge 4
    var newUsername by remember { mutableStateOf(user1.username)}
    var newAge by remember { mutableStateOf(user1.age.toString())}
    var newFriendName by remember { mutableStateOf("")}


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ProfileContent(
            //var
            user = user1,
            friends = friends,
            newUsername = newUsername,
            newAge = newAge,
            newFriendName = newFriendName,
            // Actions
            onUsernameChange = { newUsername = it },
            onAgeChange = { newAge = it },
            onFriendNameChange = { newFriendName = it },
            onLike = { user1 = user1.copy(likeCount = user1.likeCount +1) },
            onUpdateProfile = {
                val ageInt = newAge.toIntOrNull()
                if (ageInt != null){
                    user1 = user1.updateProfile(newUsername, ageInt)
                }
            },
            onAddFriend = {
                user1.addFriend(friends, newFriendName)
                newFriendName = ""
            },
            onRemoveFriend = {
                friendName -> user1.removeFriend(friends, friendName)
            }

        )
    }
}

@Composable
fun ProfileContent(
    //Vars
    user: User,
    friends: List<String>,
    newUsername: String,
    newAge: String,
    newFriendName: String,
    // actions
    onUsernameChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onFriendNameChange: (String) -> Unit,
    onLike: () -> Unit,
    onUpdateProfile: () -> Unit,
    onAddFriend: () -> Unit,
    onRemoveFriend: (String) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        UserProfileCard(user)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = newUsername,
            onValueChange = onUsernameChange,
            label = { Text("Edit Username") },
            modifier = Modifier.fillMaxWidth()
        )

        // Challeneg 6

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newAge,
            onValueChange = onAgeChange,
            label = { Text("Edit Age")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onUpdateProfile,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Profile")
        }
        //Chllg 7
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newFriendName,
            onValueChange = onFriendNameChange,
            label = { Text("Enter new Friends name")},
            modifier = Modifier.fillMaxWidth()
        )
// Challenge 8
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onAddFriend,
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Add Friend")
        }

        Spacer(modifier = Modifier.height(12.dp))

        FriendList(
            friends = friends,
            onRemoveFriend = onRemoveFriend
        )
// Challenge 9
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Button(
                onClick = onLike,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Like")
            }
        }
    }

}


@Composable
fun UserProfileCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),

        //new
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(
                            Color(0xFFE8DEF8),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "RD",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF670A4)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = user.fulName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = user.username,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    //Verified
                    if(user.isVerified){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 5.dp)
                        ){
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = "Verified",
                                tint = Color(0xFF3A913D),
                                modifier = Modifier.size(15.dp)
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = "Verified profile",
                                fontSize = 12.sp,
                                color = Color(0xFF3A913D),
                                fontWeight = FontWeight.Medium

                            )
                        }
                    }
                }
            }

          //  Text(text = user.fulName)
        //    Text(text = user.username)
            //Challenge 5
           // Text(text = "Age: ${user.age}")
            //Text(text = "Birthday: ${user.birthday}")
            //Text(text = "Address: ${user.address}")
           // Text(text = "Verified: ${if (user.isVerified)"!" else "?"}")
           // Text(text = "Likes: ${user.likeCount}")
           // Text(text = "Age Group: ${user.getAgeGroup(user.age)}")


            Spacer(modifier = Modifier.height(18.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Personal Info",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Age ${user.age}")
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Birthday ${user.birthday}")
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Address ${user.address}")
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Likes ${user.likeCount}")
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Age Group ${user.getAgeGroup(user.age)}")

        }
    }
}


@Composable
fun FriendItem(friendName: String, onRemoveFriend: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Text(
                text = friendName,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = onRemoveFriend
            ){
                Text("Remove")
            }
        }
    }
}

@Composable
fun FriendList(friends: List<String>, onRemoveFriend: (String) -> Unit) {
    Text(
        text = "Friends (${friends.size})",
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
    )

    if (friends.isEmpty()){
        Text("No Friends Added Yet!")
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ){
            items(friends){friend ->
                FriendItem(
                    friendName = friend,
                    onRemoveFriend = { onRemoveFriend(friend) }
                )

            }
        }
    }
}

