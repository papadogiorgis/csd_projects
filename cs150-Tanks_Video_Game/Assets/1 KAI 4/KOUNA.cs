using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class KOUNA : MonoBehaviour
{
   public float speed;
   public void Update()
   {
       transform.position = new Vector3(10,1, Mathf.Sin(Time.time+1.57f) * speed);
   }        
}
