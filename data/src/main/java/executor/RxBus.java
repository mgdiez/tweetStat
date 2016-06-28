/**
 * Copyright (C) 2016 Marc Gonzalez Diez Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package executor;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Implementation of an Event Bus with ReactiveX Java
 */
public class RxBus {

    /**
     * Singleton Pattern
     */
    private static RxBus RXBUS_INSTANCE;

    public static RxBus getInstance() {
        if (RXBUS_INSTANCE == null) {
            RXBUS_INSTANCE = new RxBus();
        }
        return RXBUS_INSTANCE;
    }

    /**
     * Create SerializedSubject
     */
    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    /**
     *
     * @param o Object to be send as an event.
     */
    public void send(Object o) {
        bus.onNext(o);
    }

    /**
     *
     * @return the Observable form of the Bus to be used as a receptor.
     */
    public Observable<Object> toObservable() {
        return bus;
    }

    /**
     *
     * @return true if some Observer is subscribed to the bus.
     */
    public boolean hasObservers() {
        return bus.hasObservers();
    }
}