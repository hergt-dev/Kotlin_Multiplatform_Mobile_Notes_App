package de.hergt.kmm.notes.android.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.hergt.kmm.notes.android.modules.IconTextFloatingActionButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun NotesView() {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val listState = rememberLazyListState()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val mViewModel = NotesViewModel(context)
    val mNotes by mViewModel.getAllNotes().collectAsState(initial = listOf())
    val mSelectedNote by mViewModel.selectedNote.collectAsState()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Titel",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h5
                )
                TextField(
                    value = mSelectedNote?.mTitle.orEmpty(),
                    onValueChange = {
                        mViewModel.updateTitle(it)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "Text",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h5
                )
                TextField(
                    value = mSelectedNote?.mText.orEmpty(),
                    onValueChange = {
                        mViewModel.updateText(it)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        mSelectedNote?.let {
                            mViewModel.upsertNote(it)
                            mViewModel.unselectNote()
                        }
                        focusManager.clearFocus()
                        keyboard?.hide()
                        scope.launch { bottomSheetState.hide() }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text("Speichern")
                }

                Divider()

                Button(
                    onClick = {
                        mSelectedNote?.let {
                            mViewModel.deleteNoteById(it.mId)
                            mViewModel.unselectNote()
                        }
                        focusManager.clearFocus()
                        keyboard?.hide()
                        scope.launch { bottomSheetState.hide() }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text("Löschen")
                }
            }
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            LazyColumn(state = listState) {
                items(
                    items = mNotes.sortedByDescending { it.mDate },
                    key = {
                        it.mId
                    }
                ) { note ->
                    Card(
                        onClick = {
                            mViewModel.selectNote(note.mId)
                            scope.launch { bottomSheetState.show() }
                        },
                        modifier = Modifier.padding(all = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = note.mTitle.orEmpty(),
                                style = MaterialTheme.typography.h5
                            )
                            Divider(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .fillMaxWidth()
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = mViewModel.mTimeUtil.convertTimeMillisToDateString(note.mDate),
                                    style = MaterialTheme.typography.overline
                                )
                            }
                            Text(
                                text = note.mText.orEmpty(),
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }

                if (mNotes.isEmpty()) {
                    item {
                        Card(
                            onClick = {  },
                            modifier = Modifier.padding(all = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "Keine Notizen gespeichert",
                                    modifier = Modifier.fillParentMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h4
                                )
                                Divider(
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                Text(
                                    text = "Nutze das + um eine neue Notiz zu schreiben",
                                    modifier = Modifier.fillParentMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.size(64.dp))
                }
            }
            IconTextFloatingActionButton(
                text = "Hinzufügen",
                icon = Icons.Default.Add,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
            ) {
                mViewModel.selectNewNote()
                scope.launch { bottomSheetState.show() }
            }
        }
    }
}

@Preview
@Composable
fun NotesView_Preview() {
    NotesView()
}