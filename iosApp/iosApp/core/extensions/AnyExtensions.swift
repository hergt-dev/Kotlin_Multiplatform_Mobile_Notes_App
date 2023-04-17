//
//  AnyExtensions.swift
//  iosApp
//
//  Created by Leon Hergt on 17.04.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

extension Optional where Wrapped == String {
    
    func orEmpty() -> String {
        return self ?? ""
    }
    
}
