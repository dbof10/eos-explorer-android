package dependencies

object Versions {
    const val androidCompileSdkVersion = 30
    const val androidMinSdkVersion = 23
    const val androidTargetSdkVersion = 30

    private const val versionMajor = 0
    private const val versionMinor = 1
    private const val versionPatch = 0
    const val androidVersionCode = (versionMajor * 100 + versionMinor * 10 + versionPatch )

    const val androidVersionName = "$versionMajor.$versionMinor.$versionPatch"
}
