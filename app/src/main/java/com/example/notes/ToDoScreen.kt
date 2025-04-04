package com.example.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun ToDoScreen() {
    val context = LocalContext.current.applicationContext
    val db = NoteRoomDatabase.getDatabase(context)
    val repository = NoteRepository(db.noteDao())
    val vm: NoteViewModel = viewModel(factory = NoteViewModelFactory(repository))
    vm.getAllTypes()

    val selectedType by vm.selectedType.collectAsState()
    val notes by vm.notes.collectAsState()
    val title by vm.title.collectAsState()
    val content by vm.content.collectAsState()
    val type by vm.type.collectAsState()
    val date by vm.date.collectAsState()
    val isDone by vm.isDone.collectAsState()
    val types by vm._types.collectAsState()
    var selectedNote by remember { mutableStateOf<Note?>(null) }

    var isAlert by remember { mutableStateOf(false) }
    var isAdd by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = selectedType ?: "All Notes", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(notes) { note ->
                    var expanded by remember { mutableStateOf(false) }
                    val formattedDate = remember(note.date) {
                        SimpleDateFormat("dd.MM.yyyy").format(Date(note.date))
                    }
                    val formattedTime = remember(note.date) {
                        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(note.date))
                    }

                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(24.dp))
                                .background(Color(0xFFFFF0F0))
                                .padding(4.dp)
                        ) {
                            TextButton(
                                onClick = { expanded = !expanded },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                                    .background(
                                        if (note.isDone) Color(0xFFA5D6A7) else Color(0xFFD3D3D3)
                                    ),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    // 📅 Дата и время
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Date: $formattedDate",
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = "Time: $formattedTime",
                                            fontSize = 14.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    // 📝 Заголовок + контент + иконки
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(end = 8.dp),
                                            text = "${note.title}\n${note.content}",
                                            fontSize = 14.sp,
                                            maxLines = if (expanded) Int.MAX_VALUE else 1
                                        )

                                        if (expanded) {
                                            IconButton(onClick = {
                                                selectedNote = note
                                                vm.title.value = note.title
                                                vm.content.value = note.content
                                                vm.type.value = note.type
                                                vm.date.value = note.date
                                                vm.isDone.value = note.isDone
                                                isAlert = true
                                                isAdd = false
                                            }) {
                                                Icon(
                                                    Icons.Default.Create,
                                                    contentDescription = "Edit"
                                                )
                                            }

                                            IconButton(onClick = { vm.deleteNote(note) }) {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = "Delete"
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = {
                            isAlert = true
                            isAdd = true
                            vm.date.value = System.currentTimeMillis()
                            vm.timestamp.value = System.currentTimeMillis()
                        }) {
                            Icon(Icons.Default.AddCircle, contentDescription = "Add")
                        }
                    }
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        item {
                            Button(
                                onClick = {
                                    vm.setType(null)
                                    vm.type.value = ""
                                },
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .height(36.dp)
                                    .clip(RoundedCornerShape(55.dp)),
                                shape = RoundedCornerShape(55.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                ),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                            ) {
                                Text("All")
                            }
                        }

                        items(types) { t ->
                            Button(
                                onClick = {
                                    vm.setType(t)
                                    vm.type.value = t
                                },
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .height(36.dp)
                                    .clip(RoundedCornerShape(55.dp)),
                                shape = RoundedCornerShape(55.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                ),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                            ) {
                                Text(t)
                            }
                        }
                    }
                }
            }
        }

        if (isAlert) {
            AlertDialog(
                onDismissRequest = { isAlert = false },
                title = { Text(if (isAdd) "Add New Note" else "Edit Note") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { vm.title.value = it },
                            label = { Text("Title") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = content,
                            onValueChange = { vm.content.value = it },
                            label = { Text("Content") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = type,
                            onValueChange = { vm.type.value = it },
                            label = { Text("Type (e.g., sport, work)") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Text("Done")
                            Spacer(modifier = Modifier.width(8.dp))
                            Checkbox(
                                checked = isDone,
                                onCheckedChange = { vm.isDone.value = it }
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (isAdd) {
                            vm.addNote(
                                title,
                                content,
                                type,
                                date,
                                isDone
                            )
                        } else if (selectedNote != null) {
                            selectedNote?.let {
                                vm.updateNote(
                                    it.copy(
                                        title = title,
                                        content = content,
                                        type = type,
                                        date = date,
                                        isDone = isDone
                                    )
                                )
                            }
                        }
                        vm.title.value = ""
                        vm.content.value = ""
                        vm.isDone.value = false
                        vm.type.value = selectedType ?: "" // Avoid null type
                        isAlert = false
                    }) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        isAlert = false
                        vm.title.value = ""
                        vm.content.value = ""
                        vm.isDone.value = false
                        vm.type.value = selectedType ?: ""
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
