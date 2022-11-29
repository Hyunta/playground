package com.example.springjpaground.reactiveprogramming;

import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

/**
 * Reactive Streams - Operators
 * Publisher -> [Data1] -> Operator -> [Data2] -> Op2 -> [Data3] -> Subscriber
 * 중간중간 데이터 가공이 이뤄지도록 하는 것을 Operator라고 한다.
 */
@Slf4j
public class PubSub2 {
    public static void main(String[] args) {
        Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1)
                .limit(10)
                .collect(Collectors.toList()));
//        Publisher<Integer> mapPub = mapPub(pub, s -> s * 10);
//        Publisher<Integer> sumPub = sumPub(pub);
        Publisher<Integer> reducePub = reducePub(pub, 0, (a, b) -> a + b);
        reducePub.subscribe(LogSub());
    }

    /**
     * 1,2,3,4,5
     * 0 -> (0,1) -> 0 + 1 = 1
     * 1 -> (1,2) -> 1 + 2 = 3
     * 2 -> (3,3) -> 3 + 3 = 6
     * 3 -> (6,4) -> 6 + 4 = 10
     * 4 -> (10, 5) -> 10 + 5 = 15
     * onComplete
     */
    private static Publisher<Integer> reducePub(Publisher<Integer> pub, int init,
                                                BiFunction<Integer, Integer, Integer> bf) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> subscriber) {
                pub.subscribe(new DelegateSub(subscriber) {
                    int result = init;

                    @Override
                    public void onNext(Integer item) {
                        result = bf.apply(result, item);
                    }

                    @Override
                    public void onComplete() {
                        sub.onNext(result);
                        sub.onComplete();
                    }
                });
            }
        };
    }

    private static Publisher<Integer> sumPub(Publisher<Integer> pub) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                pub.subscribe(new DelegateSub(sub) {
                    int sum = 0;

                    @Override
                    public void onNext(Integer item) {
                        sum += item;
                    }

                    @Override
                    public void onComplete() {
                        sub.onNext(sum);
                        sub.onComplete();
                    }
                });
            }
        };
    }

    private static Publisher<Integer> mapPub(Publisher<Integer> pub, Function<Integer, Integer> f) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                pub.subscribe(new DelegateSub(sub) {
                    @Override
                    public void onNext(Integer item) {
                        sub.onNext(f.apply(item));
                    }
                });
            }
        };
    }

    public static class DelegateSub implements Subscriber<Integer> {

        Subscriber sub;

        public DelegateSub(Subscriber sub) {
            this.sub = sub;
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            sub.onSubscribe(subscription); // 하는 일 없이 중개만 한다.
        }

        @Override
        public void onNext(Integer item) {
            sub.onNext(item);
        }

        @Override
        public void onError(Throwable throwable) {
            sub.onError(throwable);
        }

        @Override
        public void onComplete() {
            sub.onComplete();
        }
    }

    private static Subscriber<Integer> LogSub() {
        return new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                log.debug("onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                log.debug("onNext : {}", item);
            }

            @Override
            public void onError(Throwable throwable) {
                log.debug("onError : {}", throwable);
            }

            @Override
            public void onComplete() {
                log.debug("onComplete");
            }
        };
    }

    private static Publisher<Integer> iterPub(List<Integer> iter) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                sub.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        try {
                            iter.forEach(s -> sub.onNext(s));
                            sub.onComplete();
                        } catch (Throwable t) {
                            sub.onError(t);
                        }
                    }

                    @Override
                    public void cancel() {
                    }
                });
            }
        };
    }
}
