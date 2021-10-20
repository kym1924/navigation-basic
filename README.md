<div>
<img src="https://img.shields.io/badge/Android-3DDC84?style=flat&logo=Android&logoColor=white" />
<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=Kotlin&logoColor=white" />
<img src="https://img.shields.io/badge/writer-kym1924-yellow?&style=flat&logo=Android"/>
</div>

# Navigation-Component
Search app for Github Repositories using *Navigation-Component*.
<br><br>
<img width=360 height=760 src="https://user-images.githubusercontent.com/63637706/138022180-bd47e2a8-ac13-4c2f-82cb-617e976bca4d.gif"/>

#### 1. Initialize
```groovy
// build.gradle in Module
implementation "androidx.navigation:navigation-fragment-ktx:2.3.5"
implementation "androidx.navigation:navigation-ui-ktx:2.3.5"
```
<br>

#### 2. Create Navigation Graph
*res/navigation/nav_graph.xml*
```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/nav_graph">
    	
</navigation>
```
<br>

#### 2-1. Create NavHostFragment
*activity_main.xml*
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".presentation.main.MainActivity">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:defaultNavHost="true"
        app:navGraph="@navigation/nav_graph" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
Create *FragmentContainerView* in Activity.
  - `android:name` : Class of *NavHost* implementation.
  - `app:defaultNavHost` : NavHostFragment intercepts the system Back button. *Be sure to specify only one defaultNavHost*.
  - `app:navGraph` : Connect with *a navigation graph*.
<br>

#### 3. Add destinations
<div>
<img height=550 src="https://user-images.githubusercontent.com/63637706/138027007-b0be3f57-59d0-447a-8cd4-4c4f93ee4481.png"/>
<img height=550 src="https://user-images.githubusercontent.com/63637706/138027058-bb541a48-805d-48d0-9cc1-f26467e8e6dc.png"/>
</div>
<br>

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_graph"
    app:startDestination="@id/startFragment">

    <fragment
        android:id="@+id/startFragment"
        android:name="com.example.navigation.presentation.start.StartFragment"
        android:label="fragment_start"
        tools:layout="@layout/fragment_start"/>
</navigation>
```
- *Fragment* can be added to navigation graph with the `<fragment>` tag.
  - `android:id` : The Id field is used to refer to the destination in code.
  - `android:name` : The name field is class name.
  - `android:label`: The label field contains the use-readable name of the destination.
  - `tools:layout` : The layout field is preview layout.
- *DialogFragment* can be added to navigation graph with the `<dialog>` tag.
- *Activity* can be added to navigation graph with the `<activity>` tag.
<br>

#### 4. Connect destinations
*Actions are represented in the navigation graph as arrows.*
<br><br>
<img src="https://user-images.githubusercontent.com/63637706/138028092-5c5425f4-a940-4c99-9046-4c8d99a827a7.png"/>
<br>
```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_graph"
    app:startDestination="@id/startFragment">

    <fragment
        android:id="@+id/startFragment"
        android:name="com.example.navigation.presentation.start.StartFragment"
        android:label="fragment_start"
        tools:layout="@layout/fragment_start" >
        <action
            android:id="@+id/action_startFragment_to_searchFragment"
            app:destination="@id/searchFragment" />
    </fragment>
    <fragment
        android:id="@+id/searchFragment"
        android:name="com.example.navigation.presentation.search.SearchFragment"
        android:label="fragment_search"
        tools:layout="@layout/fragment_search" />
</navigation>
```
*Action* can be added to navigation graph with the `<action>` tag.
- `android:id` : The id field is the Id of action.
- `app:destination` : The destination field is the Id of destination.
<br>

#### 4-1. popUpTo and popUpToInclusive
With each navigation action, a destination is added to *the back stack*. 
<br><br>
<img src="https://user-images.githubusercontent.com/63637706/138044898-e190f74e-f808-480b-9a6d-209ce1634909.png"/>

- What if we go back to startFragment from repositoriesFragment?
- The backstack will contain startFragment, searchFragment, repositoriesFragment and startFragment.
- `app:popUpTo` and `app:popUpToInclusive` will prevent this unnecessary repetition. *In some cases, it may be necessary repetition.*

```xml
<fragment
    android:id="@+id/repositoriesFragment"
    android:name="com.example.navigation.presentation.repositories.RepositoriesFragment"
    android:label="fragment_repositories"
    tools:layout="@layout/fragment_repositories">
    <argument
        android:name="id"
        app:argType="string" />
    <action
        android:id="@+id/action_repositoriesFragment_to_startFragment"
        app:destination="@id/startFragment"
        app:popUpTo="@id/startFragment"
        app:popUpToInclusive="true" />
</fragment>
```
- `app:popUpTo` : Moving while deleting from itself to the destination of the back stack.
- `app:popUpToInclusive` :  If true, also pop first destination. if false, back stack will have two destination instances.
<br>

#### 5. Navigate destination with NavController and action
Navigate to the destination using a *NavController* that manages app navigation within a NavHost.
- Fragment.findNavController()
- View.findNavController()
- Activity.findNavController(viewId: Int)

```kotlin
// Fragment
binding.btnStartSearch.setOnClickListener { 
    findNavController().navigate(R.id.action_startFragment_to_searchFragment)
}

// View
binding.btnStartSearch.setOnClickListener { view ->
    view.findNavController().navigate(R.id.action_startFragment_to_searchFragment)
}
```
<br>

#### 6. Animation when switch destinations in navigation graph
*Animations* can be declared in `<action>` as follows.
<br>
```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_graph"
    app:startDestination="@id/startFragment">

    <fragment
        android:id="@+id/startFragment"
        android:name="com.example.navigation.presentation.start.StartFragment"
        android:label="fragment_start"
        tools:layout="@layout/fragment_start" >
        <action
            android:id="@+id/action_startFragment_to_searchFragment"
            app:destination="@id/searchFragment"
            app:enterAnim="@anim/enter_from_right"
            app:exitAnim="@anim/exit_to_left"
            app:popEnterAnim="@anim/enter_from_left"
            app:popExitAnim="@anim/exit_to_right" />
    </fragment>
    <fragment
        android:id="@+id/searchFragment"
        android:name="com.example.navigation.presentation.search.SearchFragment"
        android:label="fragment_search"
        tools:layout="@layout/fragment_search" />
</navigation>
```

- `app:enterAnim` : Animation when target enters the screen.
- `app:exitAnim` : Animate when the target leaves the screen.
- `app:popEnterAnim` : Animate when the target enters the screen via a pop action.
- `app:popExitAnim` : Animate when the target leaves the screen via a pop action.
<br>

#### 6-1. Animation when switch destinations in kotlin
```kotlin
val options = navOptions {
    anim {
        enter = R.anim.enter_from_right
        exit = R.anim.exit_to_left
        popEnter = R.anim.enter_from_left
        popExit = R.anim.exit_to_right
    }
}

binding.btnStartSearch.setOnClickListener {
    findNavController().navigate(R.id.action_startFragment_to_searchFragment, null, options)
}
```
<br>

#### 7. Pass data between destinations Using Bundle
1. *Pass* value written in the EditText of the SearchFragment to RepositoriesFragment.
   ```kotlin
   val bundle = bundleOf("id" to binding.etSearchId.text.toString())
   findNavController().navigate(R.id.action_searchFragment_to_repositoriesFragment, bundle)
   // findNavController().navigate(R.id.repositoriesFragment, bundle)
   ```

2. *Get* value passe in RepositoriesFragment.
   ```kotlin
   val id = arguments?.getString("id")
   ```
<br>

#### 7-1. Pass data between destinations Using Safe args
1. Gradle Setting.
   ```groovy
   // build.gradle in Project
   dependencies {
       classpath "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5"
   }
   
   // build.gradle in Module
   plugins {
       id 'androidx.navigation.safeargs.kotlin'
   }
   ```

2. Create `<argument>` in Navigation Graph.
   ```xml
   <fragment
       android:id="@+id/searchFragment"
       android:name="com.example.navigation.presentation.search.SearchFragment"
       android:label="fragment_search"
       tools:layout="@layout/fragment_search">
       <action
           android:id="@+id/action_searchFragment_to_repositoriesFragment"
           app:destination="@id/repositoriesFragment" />
   </fragment>
   <fragment
       android:id="@+id/repositoriesFragment"
       android:name="com.example.navigation.presentation.repositories.RepositoriesFragment"
       android:label="fragment_repositories"
       tools:layout="@layout/fragment_repositories">
       <argument
           android:name="id"
           app:argType="string"/>
           <!-- Can create default value using android:defaultValue="value" --> 
       <action
           android:id="@+id/action_repositoriesFragment_to_startFragment"
           app:destination="@id/startFragment"
           app:popUpTo="@id/startFragment"
           app:popUpToInclusive="true" />
   </fragment>
   ```

3. *Pass* value written in the EditText of the SearchFragment to RepositoriesFragment.
   ```kotlin
   val value = binding.etSearchId.text.toString()
   val action = SearchFragmentDirections.actionSearchFragmentToRepositoriesFragment(value)
   findNavController().navigate(action)
   ```

4. *Get* value passed in RepositoriesFragment.
   ```kotlin
   val args by navArgs<RepositoriesFragmentArgs>()
   val id = args.id
   ```
<br>

#### 7-2. Supported argument types

| Type                | app:argType syntax                                           | Support for default values                                   | Handled by routes | Nullable |
| ------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ----------------- | -------- |
| Integer             | app:argType="integer"                                        | Yes                                                          | Yes               | No       |
| Float               | app:argType="float"                                          | Yes                                                          | Yes               | No       |
| Long                | app:argType="long"                                           | Yes - Default values must always end with an 'L' suffix (e.g. "123L"). | Yes               | No       |
| Boolean             | app:argType="boolean"                                        | Yes - "true" or "false"                                      | Yes               | No       |
| String              | app:argType="string"                                         | Yes                                                          | Yes               | Yes      |
| Resource Reference  | app:argType="reference"                                      | Yes - Default values must be in the form of "@resourceType/resourceName" (e.g. "@style/myCustomStyle") or "0" | Yes               | No       |
| Custom Parcelable   | app:argType="`<type>`",<br>where `<type>` is the fully-qualified class name of the `Parcelable` | Supports a default value of "@null".<br>Does not support other default values. | No                | Yes      |
| Custom Serializable | app:argType="`<type>`",<br>where `<type>` is the fully-qualified class name of the `Serializable` | Supports a default value of "@null".<br/>Does not support other default values. | No                | Yes      |
| Custom Enum         | app:argType="`<type>`",<br>where `<type>` is the fully-qualified class name of the `enum` | Yes - Default values must match the unqualified name (e.g. "SUCCESS" to match MyEnum.SUCCESS). | No                | No       |
<br>

##### Reference

- https://developer.android.com/guide/navigation
- https://developer.android.com/guide/navigation/navigation-getting-started
- https://developer.android.com/guide/navigation/navigation-create-destinations
- https://developer.android.com/guide/navigation/navigation-animate-transitions
- https://developer.android.com/codelabs/android-navigation#0
- https://developer.android.com/codelabs/basic-android-kotlin-training-fragments-navigation-component#0
