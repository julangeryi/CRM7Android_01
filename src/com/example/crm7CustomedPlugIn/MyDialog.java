package com.example.crm7CustomedPlugIn;

import com.example.crm7demoproject.R;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyDialog extends Dialog {

	public MyDialog(Context context, int theme) {
		super(context, theme);
	}

	public MyDialog(Context context) {
		super(context);
	}

	/**
	 * Helper class for creating a custom dialog
	 */
	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String backButtonText;
		private String confirmButtonText;
		private View contentView;

		private DialogInterface.OnClickListener backButtonClickListener, confirmButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		public Builder setBackButton(int backButtonText, DialogInterface.OnClickListener listener) {
			this.backButtonText = (String) context.getText(backButtonText);
			this.backButtonClickListener = listener;
			return this;
		}

		public Builder setBackButton(String backButtonText, DialogInterface.OnClickListener listener) {
			this.backButtonText = backButtonText;
			this.backButtonClickListener = listener;
			return this;
		}

		public Builder setConfirmButton(int confirmButtonText, DialogInterface.OnClickListener listener) {
			this.confirmButtonText = (String) context.getText(confirmButtonText);
			this.confirmButtonClickListener = listener;
			return this;
		}

		public Builder setConfirmButton(String confirmButtonText, DialogInterface.OnClickListener listener) {
			this.confirmButtonText = confirmButtonText;
			this.confirmButtonClickListener = listener;
			return this;
		}

		public MyDialog create() {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			final MyDialog dialog = new MyDialog(context, R.style.Dialog);

			View layout = inflater.inflate(R.layout.my_dialog, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			((TextView) layout.findViewById(R.id.dialog_title)).setText(title);
			
			if (message != null) {
				TextView dlgMsg = (TextView) layout.findViewById(R.id.dialog_message);
				dlgMsg.setText(message);
			} else if (contentView != null) {
				// if no message set
				((LinearLayout) layout.findViewById(R.id.dialog_content)).removeAllViews();
				((LinearLayout) layout.findViewById(R.id.dialog_content)).addView(contentView,
						new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}

			if (backButtonText != null) {
				Button bckButton = ((Button) layout.findViewById(R.id.dialog_back));
				bckButton.setText(backButtonText);

				if (backButtonClickListener != null) {
					bckButton.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							backButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
						}
					});
				}
			} else {
				layout.findViewById(R.id.dialog_back).setVisibility(View.GONE);
			}

			if (confirmButtonText != null) {
				Button cfmButton = ((Button) layout.findViewById(R.id.dialog_confirm));
				cfmButton.setText(confirmButtonText);

				if (confirmButtonClickListener != null) {
					cfmButton.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							confirmButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
						}
					});
				}
			} else {
				layout.findViewById(R.id.dialog_confirm).setVisibility(View.GONE);
			}
			dialog.setContentView(layout);
			return dialog;
		}
	}
}