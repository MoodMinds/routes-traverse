package org.moodminds.traverse;

import org.moodminds.elemental.Association;
import org.moodminds.elemental.KeyValue;
import org.moodminds.emission.Emittable;
import org.moodminds.reactive.SubscribeSupportException;

import static java.util.Objects.requireNonNull;

/**
 * The wrapping {@link Traversable} implementation of the {@link Emittable} interface.
 *
 * @param <V> the type of the emitting values
 * @param <E> the type of possible exception that might be thrown
 */
public class TraversableEmittable<V, E extends Exception> implements Traversable<V, E>, Emittable<V, E> {

    /**
     * The wrapping {@link Traversable} holder field.
     */
    private final Traversable<? extends V, ? extends E> traversable;

    /**
     * Construct the object with the given {@link Traversable} instance.
     *
     * @param traversable the given {@link Traversable} instance
     */
    public TraversableEmittable(Traversable<? extends V, ? extends E> traversable) {
        this.traversable = requireNonNull(traversable);
    }

    /**
     * {@inheritDoc}
     *
     * @param method {@inheritDoc}
     * @param traverse {@inheritDoc}
     * @param ctx {@inheritDoc}
     * @return {@inheritDoc}
     * @param <H1> {@inheritDoc}
     * @param <H2> {@inheritDoc}
     * @throws E {@inheritDoc}
     * @throws H1 {@inheritDoc}
     * @throws H2 {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public <H1 extends Exception, H2 extends Exception> boolean traverse(TraverseMethod method, Traverse<V, E, ? extends H1, ? extends H2> traverse, Association<?, ?, ?> ctx) throws E, H1, H2 {
        return method.traverse(traversable, traverse, ctx);
    }

    /**
     * {@inheritDoc}
     *
     * @param subscriber {@inheritDoc}
     * @param ctx {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws SubscribeSupportException an exception indicating that asynchronous subscription is not supported
     */
    @Override
    public void subscribe(org.reactivestreams.Subscriber<? super V> subscriber, KeyValue<?, ?>... ctx) {
        requireNonNull(subscriber);
        throw new SubscribeSupportException("Synchronous traversal only.");
    }

    /**
     * {@inheritDoc}
     *
     * @param subscriber {@inheritDoc}
     * @param ctx {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws SubscribeSupportException an exception indicating that asynchronous subscription is not supported
     */
    @Override
    public void subscribe(Subscriber<? super V, ? super E> subscriber, KeyValue<?, ?>... ctx) {
        requireNonNull(subscriber);
        throw new SubscribeSupportException("Synchronous traversal only.");
    }

    /**
     * {@inheritDoc}
     *
     * @param subscriber {@inheritDoc}
     * @param ctx {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws SubscribeSupportException an exception indicating that asynchronous subscription is not supported
     */
    @Override
    public void subscribe(org.reactivestreams.Subscriber<? super V> subscriber, Association<?, ?, ?> ctx) {
        requireNonNull(subscriber); requireNonNull(ctx);
        throw new SubscribeSupportException("Synchronous traversal only.");
    }

    /**
     * {@inheritDoc}
     *
     * @param subscriber {@inheritDoc}
     * @param ctx {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws SubscribeSupportException an exception indicating that asynchronous subscription is not supported
     */
    @Override
    public void subscribe(Subscriber<? super V, ? super E> subscriber, Association<?, ?, ?> ctx) {
        requireNonNull(subscriber); requireNonNull(ctx);
        throw new SubscribeSupportException("Synchronous traversal only.");
    }
}
