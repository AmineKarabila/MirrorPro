package com.example.magicmirror

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
data class TodoItem(val title: String, val description: String? = null, var isDone: Boolean = false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(navController: NavController) {


    MaterialTheme(
        colorScheme = darkColorScheme() // Verwendet das dunkle Farbschema
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background // Setzt die Hintergrundfarbe entsprechend des Farbschemas
        ) {
            // Zustände für neues Todo-Element
            var newTodoTitle by remember { mutableStateOf("") }
            var newTodoDescription by remember { mutableStateOf("") }
            val context = LocalContext.current
            val todos = remember { mutableStateListOf<TodoItem>().apply { addAll(loadTodoList(context)) } }

            // Layout
            Column(modifier = Modifier.fillMaxSize()) {
                // Eingabefeld für neue Todos
                TextField(
                    value = newTodoTitle,
                    onValueChange = { newTodoTitle = it },
                    label = { Text("Fügen Sie eine neue Todo hinzu") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (newTodoTitle.isNotBlank()) {
                            // Nach dem Hinzufügen eines neuen Todos
                            todos.add(0, TodoItem(newTodoTitle, newTodoDescription))
                            saveTodoList(context, todos)

                            newTodoTitle = ""
                            newTodoDescription = ""
                        }
                    })
                )

                LazyColumn(
                    modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(todos) { todo ->
                        TodoItemView(todo = todo, onTodoChange = { updatedTodo ->
                            saveTodoList(context, todos)
                            // Finden Sie den Index des zu aktualisierenden Todos
                            val index = todos.indexOfFirst { it.title == updatedTodo.title && it.description == updatedTodo.description }
                            if (index != -1) {
                                // Aktualisieren Sie den Todo-Zustand in der Liste
                                todos[index] = updatedTodo.copy()
                            }
                        }, onDelete = {
                            // Entfernen des Todos
                            todos.remove(todo)
                            saveTodoList(context, todos)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItemView(todo: TodoItem, onTodoChange: (TodoItem) -> Unit, onDelete: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = todo.isDone,
            onCheckedChange = { isChecked ->
                onTodoChange(todo.copy(isDone = isChecked))
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = todo.title,
            style = MaterialTheme.typography.titleMedium,
            textDecoration = if (todo.isDone) TextDecoration.LineThrough else null
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onDelete) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Löschen",
                tint = Color.Red
                )
        }
    }
}

fun saveTodoList(context: Context, list: List<TodoItem>) {
    val sharedPreferences = context.getSharedPreferences("todo_list_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val gson = Gson()
    val json = gson.toJson(list)
    editor.putString("todo_list", json)
    editor.apply()
}

fun loadTodoList(context: Context): MutableList<TodoItem> {
    val sharedPreferences = context.getSharedPreferences("todo_list_prefs", Context.MODE_PRIVATE)
    val gson = Gson()
    val json = sharedPreferences.getString("todo_list", null)
    val type = object : TypeToken<MutableList<TodoItem>>() {}.type
    return gson.fromJson(json, type) ?: mutableListOf()
}
