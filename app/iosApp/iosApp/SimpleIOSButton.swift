//
//  SimpleIOSButton.swift
//  iosApp
//
//  Created by Roman Romanov on 19.08.2026.
//

import SwiftUI
import Shared

class IOSNativeViewFactory: NativeViewFactory {

	static var shared = IOSNativeViewFactory()

	func createButtonView(
		label: String,
		onClick: @escaping () -> Void
	) -> UIViewController {
		let view = SimpleIOSButton(label: label, action: onClick)

		return UIHostingController(rootView: view)
	}
}

struct SimpleIOSButton: View {

	var label: String
	var action: () -> Void

    var body: some View {
		Button(action: action) {
			Text(label)
				.font(.headline)
		}
    }
}

#Preview {
	SimpleIOSButton(label: "", action: {})
}
