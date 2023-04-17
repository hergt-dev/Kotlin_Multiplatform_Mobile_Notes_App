//
//  SheetView.swift
//  iosApp
//
//  Created by Leon Hergt on 17.04.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SheetView: View {
    
    @Binding var mNote: Notes?
    @Environment(\.dismiss) var dismiss
    
    let onSucess: (String, String) -> Void
    let onDelete: () -> Void
    
    @State private var mTitle: String = ""
    @State private var mText: String = ""
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            HStack {
                Spacer()
                Button {
                    dismiss()
                } label: {
                    Image(systemName: "xmark.circle.fill")
                }
                .buttonStyle(.plain)
            }
            
            Divider()
                .padding(.vertical, 8)
            
            Text("Titel")
                .font(.title3)
                .padding(.vertical, 4)
            TextField("Titel", text: $mTitle)
                .textFieldStyle(.roundedBorder)
                .padding(.bottom, 16)
            
            Text("Text")
                .font(.title3)
                .padding(.vertical, 4)
            TextField("Text", text: $mText, axis: .vertical)
                .lineLimit(5, reservesSpace: true)
                .textFieldStyle(.roundedBorder)
                .padding(.bottom, 16)
            
            Button {
                onSucess(mTitle, mText)
            } label: {
                HStack {
                    Spacer()
                    Text("Speichern")
                    Spacer()
                }
            }
            .buttonStyle(.borderedProminent)
            .padding(.vertical, 16)
            
            Divider()
            
            Button(role: .destructive) {
                onDelete()
            } label: {
                HStack {
                    Spacer()
                    Text("Löschen")
                    Spacer()
                }
            }
            .buttonStyle(.borderedProminent)
            .padding(.vertical, 16)

            Spacer()
            
        }
        .onAppear {
            mTitle = mNote?.mTitle ?? ""
            mText = mNote?.mText ?? ""
        }
        .padding(16)
    }
}

struct SheetView_Previews: PreviewProvider {
    static var previews: some View {
        SheetView(
            mNote: .constant(
                Notes(
                    mId: UUID().integers.0,
                    mTitle: "Mocked Title",
                    mText: "Mocked Text",
                    mDate: TimeUtils().currentTimeMillis()
                )
            )
        ) {_,_ in
            
        } onDelete: {
            
        }
    }
}
