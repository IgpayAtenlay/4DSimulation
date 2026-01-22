# FourDimentions

A Java application that visualizes 4D geometry by projecting it onto 2D and rendering it in real time. Move and rotate a first-person camera through 4D space and explore how a tesseract looks when rotated in the fourth dimension.

## Features

- **4D geometry**: Renders tesseracts and other 4D meshes using triangular pyramids and a Z-buffer
- **First-person camera**: Move and rotate in 4D space (x, y, z, w)
- **4D rotation**: Yaw, pitch, and rotation in the z–w plane (fourth dimension)
- **Depth cues**: Colors encode distance along the w-axis (e.g. red vs blue) with blur and gradient for depth
- **Fullscreen**: Undecorated window with crosshairs, 600×600 viewport

## Controls

| Key | Action |
|-----|--------|
| **W / S** | Move forward / backward |
| **A / D** | Strafe left / right |
| **I / K** | Move up / down |
| **J / L** | Move ana / kata (along the w-axis) |
| **Numpad 4 / 6** | Yaw (rotate left / right) |
| **Numpad 8 / 2** | Pitch (rotate up / down) |
| **Numpad 7 / 9** | 4D rotation (rotate in z–w plane) |

## Prerequisites

- **Java** (JDK 17+ recommended; uses `record` and switch expressions)
- **EJML** (Efficient Java Matrix Library) 0.44.0 for 4D rotation matrices

### Maven dependency

```xml
<dependency>
    <groupId>org.ejml</groupId>
    <artifactId>ejml-all</artifactId>
    <version>0.44.0</version>
</dependency>
```

The project is set up as an IntelliJ IDEA module and uses the `ejml.all` library (Maven: `org.ejml:ejml-all:0.44.0`).

## Running the project

1. **IntelliJ IDEA**: Open the project, ensure the EJML library is attached, then run `Main.main()`.

The default scene is a tesseract. The app runs fullscreen; use your OS shortcut (e.g. Alt+F4) to close.

## Scenes

- **Tesseract** (default): Single tesseract in 4D space.
- **Cube**, **TriangularPyramid**, **Wall**: Other demo scenes. Change the scene in `Main.java` by passing a different `Scene` to `Control.initialize(...)`.

## Technical notes

- **4D→2D projection**: The `Eye` applies 4D rotations (yaw, pitch, z–w), then a perspective projection from 3D (x, y, z) to 2D. The w-coordinate is kept for depth-based coloring.
- **Rendering**: Meshes are decomposed into triangular pyramids, rasterized with barycentric coordinates, and drawn using a Z-buffer. Color reflects depth in z and w to suggest 4D structure.
- **Ana / Kata**: Movement along the w-axis uses the 4D terms “ana” (positive w) and “kata” (negative w).

## License

This project is provided as-is for educational and personal use.
