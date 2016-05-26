package br.com.ceunsp.projeto1.validation;

import javafx.scene.control.TextField;

public class AnoTextFild extends TextField {
	@Override
	public void replaceText(int start, int end, String text) {
		String currentText = this.getText() == null ? "" : this.getText();
		String finalText = currentText.substring(0, start) + text + currentText.substring(end);
		 int numberOfexceedingCharacters = finalText.length() - 4;
		if ((text.matches("[0-9]") || text.isEmpty()) && numberOfexceedingCharacters <= 0) {
			super.replaceText(start, end, text);
		}else if((text.matches("[0-9]") || text.isEmpty())){
			 // Otherwise, cut the the text that was going to be inserted
            String cutInsertedText = text.substring(
                    0, 
                    text.length() - numberOfexceedingCharacters
            );

            // And replace this text
            super.replaceText(start, end, cutInsertedText);
		}
	}

	@Override
	public void replaceSelection(String replacement) {
		super.replaceSelection(replacement);
	}
}
