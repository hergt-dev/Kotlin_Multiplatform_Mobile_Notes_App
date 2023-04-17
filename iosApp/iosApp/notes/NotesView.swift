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
        VStack {
            ForEach(mViewModel.mNotes, id: \.mId) {
                Text($0.mTitle ?? "")
            }
        }
        .task {
            mViewModel.getAllNotes()
        }
    }
}

struct NotesView_Previews: PreviewProvider {
    static var previews: some View {
        NotesView()
    }
}
