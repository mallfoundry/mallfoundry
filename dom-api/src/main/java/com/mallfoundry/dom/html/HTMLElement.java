package com.mallfoundry.dom.html;

import com.mallfoundry.dom.css.CSSStyleDeclaration;

public interface HTMLElement {

    CSSStyleDeclaration getStyle();

    String getTextContent();

    void setTextContent(String textContent);
}
