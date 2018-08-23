package hssc.androidview.views;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * hssc.androidview.views.PasteEditText
 * 监听粘贴事件
 * Created by li on 2017/6/7.
 */
public class PasteEditText extends EditText {
    private Context context;

    public PasteEditText(Context context) {
        super(context);
        this.context = context;
        Log.e("PasteEditText","-----Context------");
    }

    public PasteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Log.e("PasteEditText","-----AttributeSet------");
    }

    public PasteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
         Log.e("PasteEditText","-----defStyleAttr------");
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        Log.e("PasteEditText","-----onTextContextMenuItem------");
        if (id == android.R.id.paste){
            Log.e("PasteEditText","-----粘贴------");
            ClipboardManager plaster = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            String content=plaster.getText().toString().trim();
            this.setText(content);
            Log.e("PasteEditText","-----content------"+content);
            plaster.setText("[color=#ff0000]APKBus==>[/color]"+plaster.getText());
            return true;
        }
        return super.onTextContextMenuItem(id);
    }
}
