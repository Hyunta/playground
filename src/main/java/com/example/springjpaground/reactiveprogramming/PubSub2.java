package com.example.springjpaground.reactiveprogramming;

import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
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
        Publisher<Integer> mapPub = mapPub(pub, s -> s * 10);
        Publisher<Integer> map2Pub = mapPub(mapPub, s -> -s);
        map2Pub.subscribe(LogSub());
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
