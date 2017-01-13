/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.managedprovisioning.preprovisioning.terms;

import static com.android.internal.util.Preconditions.checkStringNotEmpty;

import android.text.Html;
import android.text.Spanned;

/**
 * Class responsible for storing disclaimers
 */
final class TermsDocument {
    public static final int HTML_MODE = Html.FROM_HTML_MODE_COMPACT;

    private final String mHeading;
    private final Spanned mContent;

    /**
     * Private constructor. We are expecting instances to be created from strings using {@link
     * TermsDocument#fromHtml(String, String)}
     */
    private TermsDocument(String heading, Spanned content) {
        mHeading = heading;
        mContent = content;
    }

    /**
     * Constructs a new {@link TermsDocument} object by converting html content to a {@link Spanned}
     * object
     */
    public static TermsDocument fromHtml(String heading, String htmlContent) {
        return new TermsDocument(checkStringNotEmpty(heading),
                Html.fromHtml(checkStringNotEmpty(htmlContent), HTML_MODE));
    }

    /**
     * @return Document heading
     */
    public String getHeading() {
        return mHeading;
    }

    /**
     * @return Document content
     */
    public Spanned getContent() {
        return mContent;
    }
}