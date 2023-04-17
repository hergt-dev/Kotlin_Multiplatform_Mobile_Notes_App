//
//  NotesView.swift
//  iosApp
//
//  Created by Leon Hergt on 15.04.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct NotesView: View {
    
    @StateObject var mViewModel = NotesViewModel()
    
    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(spacing: 0) {
                    ForEach(mViewModel.mNotes.sorted(by: { $0.mDate < $1.mDate }), id: \.mId) { note in
                        Card(note: note)
                            .onTapGesture {
                                mViewModel.selectNote(id: note.mId)
                            }
                    }
                }
                .task {
                    mViewModel.getAllNotes()
                }
                .sheet(isPresented: $mViewModel.mSheetVisibility) {
                    SheetView(mNote: $mViewModel.mSelectedNote) { title, text in
                        mViewModel.updateTitle(value: title)
                        mViewModel.updateText(value: text)
                        mViewModel.upsertNote()
                        mViewModel.unselectNote()
                    } onDelete: {
                        mViewModel.deleteNoteById()
                        mViewModel.unselectNote()
                    }
                }
            }
            .navigationTitle("Notizen")
            .toolbarBackground(Color.yellow, for: .navigationBar)
            .toolbarBackground(.visible, for: .navigationBar)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                        mViewModel.selectNewNote()
                    } label: {
                        HStack(spacing: 4) {
                            Text("Hinzufügen")
                            Image(systemName: "plus.circle.fill")
                        }
                    }
                    
                }
            }
        }
    }
}

struct NotesView_Previews: PreviewProvider {
    static var previews: some View {
        NotesView()
    }
}
