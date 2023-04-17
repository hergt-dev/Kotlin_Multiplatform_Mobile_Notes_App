//
//  NotesViewModel.swift
//  iosApp
//
//  Created by Leon Hergt on 15.04.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

class NotesViewModel: ObservableObject {
    
    @Published var mSelectedNote: Notes? = nil
    @Published var mNotes: [Notes] = []
    
    
    private let mTimeUtils = TimeUtils()
    private let mNotesRepo = NotesRepository(mQueries: DatabaseFactory().mNotesQueries)
    
    
    func getAllNotes() {
        
        upsertNote(note: Notes(mId: UUID().integers.0, mTitle: "Test", mText: "Text...", mDate: mTimeUtils.currentTimeMillis()))
        
        mNotesRepo.getNotes().watch { notes in
            guard let notes = notes as? Array<Notes> else { return }
            self.mNotes = notes
        }
    }
    
    func upsertNote(note: Notes) {
        if mNotes.first(where: { $0.mId == note.mId }) != nil {
            mNotesRepo.updateNote(note: note)
        } else {
            mNotesRepo.createNote(note: note)
        }
    }
    
    func deleteNoteById(id: Int64) {
        mNotesRepo.deleteNoteById(id: id)
    }
    
    func selectNewNote() {
        mSelectedNote = Notes(
            mId: UUID().integers.0,
            mTitle: nil,
            mText: nil,
            mDate: mTimeUtils.currentTimeMillis()
        )
    }
    
    func selectNote(id: Int64) {
        getNoteById(id: id).watch { note in
            guard let note = note else { return }
            self.mSelectedNote = note
        }
    }
    
    func unselectNote() {
        mSelectedNote = nil
    }
    
    func updateTitle(value: String) {
        guard let selectedNote = mSelectedNote else { return }
        mSelectedNote = selectedNote.doCopy(
            mId: selectedNote.mId,
            mTitle: value,
            mText: selectedNote.mText,
            mDate: selectedNote.mDate
        )
    }
    
    func updateText(value: String) {
        guard let selectedNote = mSelectedNote else { return }
        mSelectedNote = selectedNote.doCopy(
            mId: selectedNote.mId,
            mTitle: selectedNote.mTitle,
            mText: value,
            mDate: selectedNote.mDate
        )
    }
    
    private func getNoteById(id: Int64) -> CommonFlow<Notes> {
        return mNotesRepo.getNoteById(id: id)
    }
    
}
