//
//  Card.swift
//  iosApp
//
//  Created by Leon Hergt on 17.04.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct Card: View {
    
    let note: Notes
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 25, style: .continuous)
                .fill(.gray.opacity(0.1))
                .shadow(radius: 10)

            VStack(alignment: .leading) {
                Text(note.mTitle.orEmpty())
                    .font(.title2)
                    .foregroundColor(.black)
                
                Divider()
                
                HStack {
                    Spacer()
                    Text(TimeUtils().convertTimeMillisToDateString(timeMillis: note.mDate))
                }

                Text(note.mText.orEmpty())
                    .font(.body)
                    .foregroundColor(.gray)
            }
            .padding(20)
        }
        .padding(16)
    }
}

struct Card_Previews: PreviewProvider {
    static var previews: some View {
        Card(note: Notes(mId: UUID().integers.0, mTitle: "Mocked Title", mText: "Mocked Text", mDate: TimeUtils().currentTimeMillis()))
    }
}
