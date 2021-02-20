/*
 * Copyright 2020 Oliver Berg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package krautils.scalr

import org.imgscalr.Scalr
import java.awt.Color
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.awt.image.BufferedImageOp

/**
 * Used to define the different scaling hints that the algorithm can use.
 *
 * @see [Scalr.Method]
 */
public typealias ImageScalingMethod = Scalr.Method

/**
 * Used to define the different modes of resizing that the algorithm can use.
 *
 * @see [Scalr.Mode]
 */
public typealias ImageResizeMode = Scalr.Mode

/**
 * Used to define the different types of rotations that can be applied to an image during a resize operation.
 *
 * @see [Scalr.Rotation]
 */
public typealias ImageRotation = Scalr.Rotation

/**
 * Applies the given [operations] to the image and returns the result.
 *
 * @see [Scalr.apply]
 */
public fun BufferedImage.apply(vararg operations: BufferedImageOp): BufferedImage = Scalr.apply(this, *operations)

/**
 * Crops the image from the top-left corner to the given [width] and [height], and applies any of given optional
 * [operations] to the result before returning it.
 *
 * @see [Scalr.crop]
 */
public fun BufferedImage.cropTo(width: Int, height: Int, vararg operations: BufferedImageOp): BufferedImage =
    Scalr.crop(this, width, height, *operations)

/**
 * Crops the image to the given [x], [y], [width] and [height] and applies any of given optional [operations] to
 * the result before returning it.
 *
 * @see [Scalr.crop]
 */
public fun BufferedImage.cropTo(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    vararg operations: BufferedImageOp,
): BufferedImage =
    Scalr.crop(this, x, y, width, height, *operations)

/**
 * Applies the given [padding] around the edges of the image, using the given [color] to fill the extra padded space,
 * and then returns the result.
 *
 * @see [Scalr.pad]
 */
public fun BufferedImage.pad(
    padding: Int,
    color: Color = Color.BLACK,
    vararg operations: BufferedImageOp,
): BufferedImage =
    Scalr.pad(this, padding, color, *operations)

/**
 * Resizes the image *(whether or not the proportions of the image are kept depends on the given [resizeMode])*
 * to a `width` and `height` no larger than the given [targetSize] and applies the given optional [operations] to the
 * result before returning it.
 *
 * @see [Scalr.resize]
 */
public fun BufferedImage.resizeTo(
    targetSize: Int,
    scalingMethod: ImageScalingMethod = ImageScalingMethod.AUTOMATIC,
    resizeMode: ImageResizeMode = ImageResizeMode.AUTOMATIC,
    vararg operations: BufferedImageOp,
): BufferedImage = Scalr.resize(this, scalingMethod, resizeMode, targetSize, targetSize, *operations)

/**
 * Resizes the image *(whether or not the proportions of the image are kept depends on the given [resizeMode])*
 * to the given [targetWidth] and [targetHeight] and applies the given optional [operations] to the result before
 * returning it.
 *
 * @see [Scalr.resize]
 */
public fun BufferedImage.resizeTo(
    targetWidth: Int,
    targetHeight: Int,
    scalingMethod: ImageScalingMethod = ImageScalingMethod.AUTOMATIC,
    resizeMode: ImageResizeMode = ImageResizeMode.AUTOMATIC,
    vararg operations: BufferedImageOp,
): BufferedImage = Scalr.resize(this, scalingMethod, resizeMode, targetWidth, targetHeight, *operations)

/**
 * Resizes the image *(whether or not the proportions of the image are kept depends on the given [resizeMode])*
 * to the given [dimension] and applies the given optional [operations] to the result before returning it.
 *
 * @see [Scalr.resize]
 */
public fun BufferedImage.resizeTo(
    dimension: Dimension,
    scalingMethod: ImageScalingMethod = ImageScalingMethod.AUTOMATIC,
    resizeMode: ImageResizeMode = ImageResizeMode.AUTOMATIC,
    vararg operations: BufferedImageOp,
): BufferedImage = Scalr.resize(this, scalingMethod, resizeMode, dimension.width, dimension.height, *operations)

/**
 * Rotates the image according to the given [rotation] and applies the optional [operations] to the result before
 * returning it.
 */
public fun BufferedImage.rotate(rotation: ImageRotation, vararg operations: BufferedImageOp): BufferedImage =
    Scalr.rotate(this, rotation, *operations)