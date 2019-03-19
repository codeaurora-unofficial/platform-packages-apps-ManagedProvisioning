/*
 * Copyright 2019, The Android Open Source Project
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
package com.android.managedprovisioning.finalization;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import com.android.managedprovisioning.R;
import com.android.managedprovisioning.common.RepeatingVectorAnimation;
import com.android.managedprovisioning.common.SetupGlifLayoutActivity;
import com.android.managedprovisioning.common.Utils;
import com.android.managedprovisioning.model.CustomizationParams;
import com.android.managedprovisioning.model.ProvisioningParams;
import com.android.setupwizardlib.GlifLayout;

/**
 * This activity shows the final screen of the admin integrated flow.
 */
public class FinalScreenActivity extends SetupGlifLayoutActivity {

    static String EXTRA_PROVISIONING_PARAMS =
        "com.android.managedprovisioning.PROVISIONING_PARAMS";

    private RepeatingVectorAnimation mRepeatingVectorAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ProvisioningParams provisioningParams =
                getIntent().getParcelableExtra(EXTRA_PROVISIONING_PARAMS);
        if (provisioningParams == null) {
            throw new IllegalStateException("Can't show UI without provisioning params.");
        }
        initializeUi(provisioningParams);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mRepeatingVectorAnimation != null) {
            mRepeatingVectorAnimation.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRepeatingVectorAnimation != null) {
            mRepeatingVectorAnimation.stop();
        }
    }

    private void initializeUi(ProvisioningParams params) {
        final int headerResId = R.string.device_provisioning_finished;
        final int titleResId = R.string.device_provisioning_finished;

        final CustomizationParams customizationParams =
            CustomizationParams.createInstance(params, this, mUtils);
        initializeLayoutParams(R.layout.final_screen, headerResId,
            customizationParams.mainColor, customizationParams.statusBarColor);
        setTitle(titleResId);

        GlifLayout layout = findViewById(R.id.setup_wizard_layout);
        final ImageView imageView = layout.findViewById(R.id.animation);
        final AnimatedVectorDrawable animatedVectorDrawable =
                (AnimatedVectorDrawable) imageView.getDrawable();
        mRepeatingVectorAnimation = new RepeatingVectorAnimation(animatedVectorDrawable);
        layout.findViewById(R.id.done_button).setOnClickListener(v -> onDoneButtonPressed());

        if (Utils.isSilentProvisioning(this, params)) {
            onDoneButtonPressed();
        }
    }

    private void onDoneButtonPressed() {
        finish();
    }


}
