/*
 * Copyright 2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazon.alexa.auto.navigation.handlers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazon.alexa.auto.aacs.common.AACSMessage;
import com.amazon.alexa.auto.aacs.common.HourOfOperation;
import com.amazon.alexa.auto.aacs.common.TemplateRuntimeMessages;
import com.amazon.alexa.auto.aacs.common.navi.LocalSearchDetailTemplate;
import com.amazon.alexa.auto.aacs.common.navi.LocalSearchListTemplate;
import com.amazon.alexa.auto.apis.app.AlexaApp;
import com.amazon.alexa.auto.apis.session.SessionViewController;
import com.amazon.alexa.auto.navigation.R;
import com.amazon.alexa.auto.navigation.poi.LocalSearchListAdapter;
import com.amazon.alexa.auto.navigation.providers.NaviProvider;
import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class LocalSearchDirectiveHandler {
    private static final String TAG = LocalSearchDirectiveHandler.class.getSimpleName();
    private final WeakReference<Context> mContext;
    private final NaviProvider mNaviProvider;

    public LocalSearchDirectiveHandler(WeakReference<Context> context, NaviProvider naviProvider) {
        mContext = context;
        mNaviProvider = naviProvider;
    }

    /**
     * Gets the local search list directive as a {@link AACSMessage} and launches a view.
     * @param message aacs template runtime intent.
     */
    public void renderLocalSearchListTemplate(AACSMessage message) {
        AlexaApp app = AlexaApp.from(mContext.get());
        app.getRootComponent().getComponent(SessionViewController.class).ifPresent(sessionViewController -> {
            sessionViewController.getTemplateRuntimeViewContainer().ifPresent(viewGroup -> {
                TemplateRuntimeMessages.parseLocalSearchListTemplate(message.payload)
                        .ifPresent(localSearchListTemplate -> {
                            try {
                                renderLocalSearchListView(viewGroup, localSearchListTemplate);
                                sessionViewController.setTemplateDisplayed();
                            } catch (Exception e) {
                                Log.e(TAG, "Issue inflating template: " + e);
                            }
                        });
            });
        });
    }

    /**
     * Gets the local search detail directive as a {@link AACSMessage} and launches a new activity.
     * @param message aacs template runtime intent.
     */
    public void renderLocalSearchDetailTemplate(AACSMessage message) {
        AlexaApp app = AlexaApp.from(mContext.get());
        app.getRootComponent().getComponent(SessionViewController.class).ifPresent(sessionViewController -> {
            sessionViewController.getTemplateRuntimeViewContainer().ifPresent(viewGroup -> {
                TemplateRuntimeMessages.parseLocalSearchDetailTemplate(message.payload)
                        .ifPresent(localSearchDetailTemplate -> {
                            try {
                                renderLocalSearchDetailView(viewGroup, localSearchDetailTemplate);
                                sessionViewController.setTemplateDisplayed();
                            } catch (Exception e) {
                                Log.e(TAG, "Issue inflating template: " + e);
                            }
                        });
            });
        });
    }

    /**
     * Clears the local search template if exists. This is so we can clear the right template, since
     * the clear template directive from aacs doesn't say which template needs to be cleared.
     * e.g. in the case where the user tries to invoke Weather template before the clear
     * template is received for the Local Search template. We don't want to clear weather template
     * with the clear template directive that was actually meant for local search
     */
    public void clearLocalSearchTemplate() {
        AlexaApp.from(mContext.get())
                .getRootComponent()
                .getComponent(SessionViewController.class)
                .ifPresent(sessionViewController -> {
                    sessionViewController.getTemplateRuntimeViewContainer().ifPresent(viewGroup -> {
                        if (viewGroup.findViewById(R.id.template_local_search_list_view) != null
                                || viewGroup.findViewById(R.id.template_local_search_detail_view) != null) {
                            Log.i(TAG, "clearLocalSearchTemplate");
                            sessionViewController.clearTemplate();
                        }
                    });
                });
    }

    /**
     * Clears existing template, if any.
     */
    public void clearTemplate() {
        AlexaApp.from(mContext.get())
                .getRootComponent()
                .getComponent(SessionViewController.class)
                .ifPresent(SessionViewController::clearTemplate);
    }

    @VisibleForTesting
    void renderLocalSearchListView(ViewGroup viewContainer, LocalSearchListTemplate localSearchListTemplate) {
        LayoutInflater layoutInflater =
                (LayoutInflater) mContext.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View inflatedView = layoutInflater.inflate(R.layout.local_search_list, null);
        inflatedView.setId(R.id.template_local_search_list_view);
        viewContainer.addView(
                inflatedView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LocalSearchListAdapter adapter =
                new LocalSearchListAdapter(mNaviProvider, new WeakReference<>(this), mContext.get());
        String title = localSearchListTemplate.getTitle() != null ? localSearchListTemplate.getTitle() : "";
        TextView titleText = inflatedView.findViewById(R.id.poi_card_title_text);
        titleText.setText(title.toUpperCase());

        localSearchListTemplate.getPointOfInterests().forEach(adapter::addPOI);

        RecyclerView recyclerView = inflatedView.findViewById(R.id.poi_list);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext.get()));
    }

    @VisibleForTesting
    void renderLocalSearchDetailView(ViewGroup viewGroup, LocalSearchDetailTemplate localSearchDetailTemplate) {
        viewGroup.removeAllViews();

        LayoutInflater layoutInflater =
                (LayoutInflater) mContext.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View inflatedView = layoutInflater.inflate(R.layout.local_search_detail, null);
        inflatedView.setId(R.id.template_local_search_detail_view);
        viewGroup.addView(inflatedView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        String title = localSearchDetailTemplate.getTitle().getMainTitle();
        TextView titleText = inflatedView.findViewById(R.id.local_search_detail_title);
        titleText.setText(title);

        if (!localSearchDetailTemplate.getProvider().equals("Yelp")) {
            inflatedView.findViewById(R.id.yelp_rating_layout).setVisibility(View.GONE);
        } else {
            ImageView yelpRatingView = inflatedView.findViewById(R.id.yelp_rating_image);
            setYelpImageView(yelpRatingView, localSearchDetailTemplate);
            ((TextView) inflatedView.findViewById(R.id.yelp_review_count))
                    .setText(String.format("(%s)", localSearchDetailTemplate.getRating().getReviewCount()));
        }

        TextView hoursOfOperationView = inflatedView.findViewById(R.id.local_search_hours_of_operation);
        setHoursOfOperation(hoursOfOperationView, localSearchDetailTemplate.getHoursOfOperation());

        TextView addressText = inflatedView.findViewById(R.id.local_search_address);
        addressText.setText(localSearchDetailTemplate.getAddress());

        TextView distanceText = inflatedView.findViewById(R.id.local_search_distance);
        distanceText.setText(localSearchDetailTemplate.getTravelDistance());
        TextView etaText = inflatedView.findViewById(R.id.local_search_eta);
        etaText.setText(localSearchDetailTemplate.getTravelTime());

        TextView navigateButton = inflatedView.findViewById(R.id.navigate_button);
        navigateButton.setOnClickListener(view -> {
            mNaviProvider.startNavigation(localSearchDetailTemplate.getCoordinate());
            clearTemplate();
        });

        inflatedView.findViewById(R.id.close_button).setOnClickListener(view -> clearTemplate());
    }

    private void setYelpImageView(ImageView yelpRatingView, LocalSearchDetailTemplate localSearchDetailTemplate) {
        if (!localSearchDetailTemplate.getRating().getImage().getSources().isEmpty()) {
            String yelpImageUrl = localSearchDetailTemplate.getRating().getImage().getSources().get(0).getUrl();
            Glide.with(mContext.get()).load(yelpImageUrl).into(yelpRatingView);
        }
    }

    @VisibleForTesting
    void setHoursOfOperation(TextView hoursOfOperationView, List<HourOfOperation> hoursOfOperation) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        Optional<HourOfOperation> hoursOfOperationForSpecificDay =
                hoursOfOperation.stream()
                        .filter(hourOfOperation
                                -> hourOfOperation.getDayOfWeek().toLowerCase().equals(dayOfTheWeek.toLowerCase()))
                        .findAny();

        if (hoursOfOperationForSpecificDay.isPresent()) {
            String hours = hoursOfOperationForSpecificDay.get().getHours();
            String[] startAndEndHours = hours.split("-");

            if (startAndEndHours.length == 2) {
                final SimpleDateFormat twentyFourHourFormat = new SimpleDateFormat("HH:mm:ss");
                final SimpleDateFormat twelveHourFormat = new SimpleDateFormat("h a");

                try {
                    final Date startDateObj = twentyFourHourFormat.parse(startAndEndHours[0]);
                    final Date endDateObj = twentyFourHourFormat.parse(startAndEndHours[1]);

                    String startDateString = twelveHourFormat.format(startDateObj);
                    String endDateString = twelveHourFormat.format(endDateObj);

                    String hoursOfOperationFinalString =
                            String.format("Today: %s - %s", startDateString, endDateString);
                    hoursOfOperationView.setText(hoursOfOperationFinalString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            hoursOfOperationView.setText(R.string.closed_text);
        }
    }
}
