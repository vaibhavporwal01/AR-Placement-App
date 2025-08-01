package com.example.arplacementapp.ar

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import android.graphics.Color


class ArPlacementActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment
    private var cubeRenderable: ModelRenderable? = null
    private var placedObjectAnchor: Anchor? = null

    companion object {
        const val DRILL_ID_KEY = "DRILL_ID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drillId = intent.getStringExtra(DRILL_ID_KEY)

        if (drillId != null) {
            Log.d("ArPlacementActivity", "Received drill ID: $drillId")
        } else {
            Log.e("ArPlacementActivity", "Drill ID not found in Intent extras!")
            Toast.makeText(this, "Error: Drill information missing.", Toast.LENGTH_LONG).show()
            finish()
        }

        setContentView(com.example.arplacementapp.R.layout.activity_ar_placement)

        arFragment = supportFragmentManager.findFragmentById(com.example.arplacementapp.R.id.ar_fragment) as ArFragment

        buildCubeRenderable()

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent ->
            if (cubeRenderable == null) {
                Toast.makeText(this, "Loading 3D object...", Toast.LENGTH_SHORT).show()
                return@setOnTapArPlaneListener
            }

            if (placedObjectAnchor != null) {
                placedObjectAnchor?.detach()
                placedObjectAnchor = null
            }

            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)

            val transformableNode = TransformableNode(arFragment.transformationSystem)
            transformableNode.setParent(anchorNode)
            transformableNode.renderable = cubeRenderable
            transformableNode.select()

            placedObjectAnchor = anchor

            Toast.makeText(this, "Drill marker placed!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun buildCubeRenderable() {
        MaterialFactory.makeOpaqueWithColor(this, com.google.ar.sceneform.rendering.Color(Color.RED))
            .thenAccept { material ->
                cubeRenderable = ShapeFactory.makeCube(
                    com.google.ar.sceneform.math.Vector3(0.1f, 0.1f, 0.1f),
                    com.google.ar.sceneform.math.Vector3(0.0f, 0.05f, 0.0f),
                    material
                )
            }
            .exceptionally { throwable ->
                Log.e("ArPlacementActivity", "Error creating cube renderable", throwable)
                Toast.makeText(this, "Error loading 3D object.", Toast.LENGTH_LONG).show()
                null
            }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}