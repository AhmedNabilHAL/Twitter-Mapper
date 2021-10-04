package twitter;

import twitter4j.*;
import util.ObjectSource;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * A Twitter source that plays back a recorded stream of tweets.
 *
 * It ignores the set of terms provided except it uses the first call to setFilterTerms
 * as a signal to begin playback of the recorded stream of tweets.
 *
 * Implements Observable - each tweet is signalled to all observers
 */
public class PlaybackTwitterSource extends TwitterSource {
    // The speedup to apply to the recorded stream of tweets; 2 means play at twice the rate
    // at which the tweets were recorded
    private final double speedup;
    private ObjectSource source = new ObjectSource("C:\\Users\\Ahmed Nabil\\final-project-starter\\TwitterMapperStarter\\data/TwitterCapture_1.jobj");
    private boolean threadStarted = false;

    public PlaybackTwitterSource(double speedup) {
        this.speedup = speedup;
    }

    private void startThread() {
        if (threadStarted) return;
        threadStarted = true;
        Thread t = new Thread() {
            long initialDelay = 1000;
            long playbackStartTime = System.currentTimeMillis() + initialDelay;
            long recordStartTime = 0;

            public void run() {
                long now;
                while (true) {
                    Object timeo = source.readObject();
                    if (timeo == null) break;
                    //Object statuso = source.readObject();
                    //if (statuso == null) break;
                    long statusTime = (Long)timeo;
                    if (recordStartTime == 0) recordStartTime = statusTime;
                    Status status = new Status() {
                        @Override
                        public Date getCreatedAt() {
                            return new Date(System.currentTimeMillis());
                        }

                        @Override
                        public long getId() {
                            return 0;
                        }

                        @Override
                        public String getText() {
                            return "its a meeee";
                        }

                        @Override
                        public int getDisplayTextRangeStart() {
                            return 0;
                        }

                        @Override
                        public int getDisplayTextRangeEnd() {
                            return 0;
                        }

                        @Override
                        public String getSource() {
                            return null;
                        }

                        @Override
                        public boolean isTruncated() {
                            return false;
                        }

                        @Override
                        public long getInReplyToStatusId() {
                            return 0;
                        }

                        @Override
                        public long getInReplyToUserId() {
                            return 0;
                        }

                        @Override
                        public String getInReplyToScreenName() {
                            return null;
                        }

                        @Override
                        public GeoLocation getGeoLocation() {
                            return new GeoLocation(37.203921, -21.732452);
                        }

                        @Override
                        public Place getPlace() {
                            return new Place() {
                                @Override
                                public String getName() {
                                    return null;
                                }

                                @Override
                                public String getStreetAddress() {
                                    return null;
                                }

                                @Override
                                public String getCountryCode() {
                                    return null;
                                }

                                @Override
                                public String getId() {
                                    return null;
                                }

                                @Override
                                public String getCountry() {
                                    return null;
                                }

                                @Override
                                public String getPlaceType() {
                                    return null;
                                }

                                @Override
                                public String getURL() {
                                    return null;
                                }

                                @Override
                                public String getFullName() {
                                    return null;
                                }

                                @Override
                                public String getBoundingBoxType() {
                                    return null;
                                }

                                @Override
                                public GeoLocation[][] getBoundingBoxCoordinates() {
                                    GeoLocation l = new GeoLocation(0.0, -0.0);
                                    GeoLocation[][] list = {{l,l,l,l}};
                                    return list;
                                }

                                @Override
                                public String getGeometryType() {
                                    return null;
                                }

                                @Override
                                public GeoLocation[][] getGeometryCoordinates() {
                                    return new GeoLocation[0][];
                                }

                                @Override
                                public Place[] getContainedWithIn() {
                                    return new Place[0];
                                }

                                @Override
                                public int compareTo(Place o) {
                                    return 0;
                                }

                                @Override
                                public RateLimitStatus getRateLimitStatus() {
                                    return null;
                                }

                                @Override
                                public int getAccessLevel() {
                                    return 0;
                                }
                            };
                        }

                        @Override
                        public boolean isFavorited() {
                            return false;
                        }

                        @Override
                        public boolean isRetweeted() {
                            return false;
                        }

                        @Override
                        public int getFavoriteCount() {
                            return 0;
                        }

                        @Override
                        public User getUser() {
                            return null;
                        }

                        @Override
                        public boolean isRetweet() {
                            return false;
                        }

                        @Override
                        public Status getRetweetedStatus() {
                            return null;
                        }

                        @Override
                        public long[] getContributors() {
                            return new long[0];
                        }

                        @Override
                        public int getRetweetCount() {
                            return 0;
                        }

                        @Override
                        public boolean isRetweetedByMe() {
                            return false;
                        }

                        @Override
                        public long getCurrentUserRetweetId() {
                            return 0;
                        }

                        @Override
                        public boolean isPossiblySensitive() {
                            return false;
                        }

                        @Override
                        public String getLang() {
                            return null;
                        }

                        @Override
                        public Scopes getScopes() {
                            return null;
                        }

                        @Override
                        public String[] getWithheldInCountries() {
                            return new String[0];
                        }

                        @Override
                        public long getQuotedStatusId() {
                            return 0;
                        }

                        @Override
                        public Status getQuotedStatus() {
                            return null;
                        }

                        @Override
                        public URLEntity getQuotedStatusPermalink() {
                            return null;
                        }

                        @Override
                        public int compareTo(Status o) {
                            return 0;
                        }

                        @Override
                        public UserMentionEntity[] getUserMentionEntities() {
                            return new UserMentionEntity[0];
                        }

                        @Override
                        public URLEntity[] getURLEntities() {
                            return new URLEntity[0];
                        }

                        @Override
                        public HashtagEntity[] getHashtagEntities() {
                            return new HashtagEntity[0];
                        }

                        @Override
                        public MediaEntity[] getMediaEntities() {
                            return new MediaEntity[0];
                        }

                        @Override
                        public SymbolEntity[] getSymbolEntities() {
                            return new SymbolEntity[0];
                        }

                        @Override
                        public RateLimitStatus getRateLimitStatus() {
                            return null;
                        }

                        @Override
                        public int getAccessLevel() {
                            return 0;
                        }
                    };
                    long playbackTime = computePlaybackTime(statusTime);
                    while ((now = System.currentTimeMillis()) < playbackTime) {
                        pause(playbackTime - now);
                    }
                    if (status.getPlace() != null) {
                        handleTweet(status);
                    }
                }
            }

            private long computePlaybackTime(long statusTime) {
                long statusDelta = statusTime - recordStartTime;
                long targetDelta = Math.round(statusDelta / speedup);
                long targetTime = playbackStartTime + targetDelta;
                return targetTime;
            }

            private void pause(long millis) {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    /**
     * The playback source merely starts the playback thread, it it hasn't been started already
     */
    protected void sync() {
        System.out.println("Starting playback thread with " + terms);

        startThread();
    }
}
