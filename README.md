# Metaphor

Metaphor is a framework that easy to use fragments in android

# How to use

## 1. Confirm your project added it in your root build.gradle at the end of repositories:

```js
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

## 2. Add it in your root build.gradle at the end of repositories:

```js
dependencies {
    implementation 'com.github.xuanwenchao:Metaphor:1.1'
}
```

## 3. add fragments to specify container in activity or fragment

```java
//åspeecific containers of Activity, It means you can show multi set of fragment in different container
@MetaphorRes(containerViewIDs = {R.id.container_number_one, R.id.container_number_two})
public class MainActivity extends AppCompatActivity {
    ......

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get or create metaphor manager
        IMetaphorManager metaphorManager = Metaphor.with(this);

        //add a set of fragment to specify serial number container (R.id.container_number_two),and show fragment who name is m_fx1
        metaphorManager.addFragment(m_fx1, m_fx2, m_fx3).showFragment(m_fx1);
    }
}
```

## 4. show fragment on parent container

```java
    //you can show fragment with tag name anywhere
    metaphorManager.showFragment("FA");
```

## 5. show fragment on subfragment

```java
    //you can call getManager to get MetaphorManager in subfragment
    IMetaphorSubFragmentManager metaphorSubFragmentManager = Metaphor.getManager(this);

    //you can show fragment with tag name anywhere 
    metaphorSubFragmentManager.showFragment("FA");
```

## 6. send message between fragment and fragment
```java
    //you can call getManager to get MetaphorManager in subfragment
    IMetaphorSubFragmentManager metaphorSubFragmentManager = Metaphor.getManager(this);

    //send message to base object
    metaphorSubFragmentManager.sendMessageToBase(0,null);
```


<div align="left">
<img src=https://user-images.githubusercontent.com/1876277/239443656-b51697e1-b974-4913-b539-df60eae7fddd.jpg  width=300 height=560/>
</div>



