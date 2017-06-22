package streaming_chunker;

/**
 *
 * @author Dmitriy Gerashenko <d.a.gerashenko@gmail.com>
 */
public interface ChunkListener {

    default public void onChunkEnd() throws Exception {
    }

    default public void onChunkBegin() throws Exception {

    }
}
