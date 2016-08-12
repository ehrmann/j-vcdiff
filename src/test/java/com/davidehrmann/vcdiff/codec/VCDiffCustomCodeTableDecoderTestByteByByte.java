package com.davidehrmann.vcdiff.codec;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class VCDiffCustomCodeTableDecoderTestByteByByte extends VCDiffCustomCodeTableDecoderTestBase {
    @Test
    public void DecodeUsingCustomCodeTable() throws Exception {
        decoder_.StartDecoding(dictionary_);
        for (int i = 0; i < delta_file_.length; ++i) {
            decoder_.DecodeChunk(delta_file_, i, 1, output_);
        }
        decoder_.FinishDecoding();
        assertArrayEquals(expected_target_, output_.toByteArray());
    }

    @Test
    public void IncompleteCustomCodeTable() throws Exception {
        delta_file_ = Arrays.copyOf(delta_file_, delta_file_header_.length - 1);
        decoder_.StartDecoding(dictionary_);
        for (int i = 0; i < delta_file_.length; ++i) {
            decoder_.DecodeChunk(delta_file_, i, 1, output_);
        }
        try {
            thrown.expect(IOException.class);
            decoder_.FinishDecoding();
        } finally {
            assertArrayEquals(new byte[0], output_.toByteArray());
        }
    }

    @Test
    public void CustomTableNoVcdTarget() throws Exception {
        decoder_.SetAllowVcdTarget(false);
        decoder_.StartDecoding(dictionary_);
        for (int i = 0; i < delta_file_.length; ++i) {
            decoder_.DecodeChunk(delta_file_, i, 1, output_);
        }
        decoder_.FinishDecoding();
        assertArrayEquals(expected_target_, output_.toByteArray());
    }
}
