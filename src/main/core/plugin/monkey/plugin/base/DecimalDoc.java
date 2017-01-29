package core.plugin.monkey.plugin.base;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class DecimalDoc extends PlainDocument {
    
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        // 若字符串为空，直接返回。
        if (str == null) {
            return;
        }
        int len = getLength();
        String s = getText(0, len);// 文本框已有的字符
        try {
            s = s.substring(0, offs) + str + s.substring(offs, len);// 在已有的字符后添加字符
            int i = Integer.parseInt(s); // 只能为正整数
            if (i < 0) { // 限制范围
                throw new Exception();
            }
        } catch (Exception e) {
            return;
        }
        super.insertString(offs, str, a);// 把字符添加到文本框
    }
    
}
