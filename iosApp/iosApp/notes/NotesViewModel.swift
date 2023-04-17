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
    
    var mSheetVisibility: Bool {
        get {
            return mSelectedNote != nil
        }
        set {  }
    }
    
    private let mTimeUtils = TimeUtils()
    private let mNotesRepo = NotesRepository(mQueries: DatabaseFactory().mNotesQueries)
    
    
    func getAllNotes() {
        mNotesRepo.getNotes().collect(collector: Collector<Array<Notes>>{ notes in
            DispatchQueue.main.async {
                self.mNotes = notes
            }
        }) { error in
            print("FLOW ERROR getAllNotes(): \(String(describing: error?.localizedDescription))")
        }
    }
    
    func upsertNote() {
        guard let note = mSelectedNote else { return }
        if let _ = mNotes.first(where: { $0.mId == note.mId }) {
            mNotesRepo.updateNote(note: note)
        } else {
            mNotesRepo.createNote(note: note)
        }
    }
    
    func deleteNoteById() {
        guard let id = mSelectedNote?.mId else { return }
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
        if let note = mNotes.first(where: { $0.mId == id }) {
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
    
}
